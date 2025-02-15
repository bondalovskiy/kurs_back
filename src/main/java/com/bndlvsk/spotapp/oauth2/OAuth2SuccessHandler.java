package com.bndlvsk.spotapp.oauth2;

import com.bndlvsk.spotapp.exception.BadRequestException;
import com.bndlvsk.spotapp.jwt.CookieUtils;
import com.bndlvsk.spotapp.jwt.JwtProvider;
import com.bndlvsk.spotapp.model.OAuth2UserWrapper;
import com.bndlvsk.spotapp.repository.SpotifyTokenRepository;
import com.bndlvsk.spotapp.service.LibraryDataSyncService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static com.bndlvsk.spotapp.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@AllArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final SpotifyTokenRepository spotifyTokenRepository;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final LibraryDataSyncService libraryDataSyncService;
    private final JwtProvider tokenProvider;
    private final URI clientAuthorizedUri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OAuth2UserWrapper principal = (OAuth2UserWrapper) authentication.getPrincipal();
        spotifyTokenRepository.saveToken(principal.getUserId(), token);
        if (!libraryDataSyncService.isLibraryDataSynchronized(principal.getUserId()))
            libraryDataSyncService.synchronize(principal.getUserId());
        if (response.isCommitted())
            return;
        String url = determineTargetUrl(request, principal);
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, url);
    }

    protected String determineTargetUrl(HttpServletRequest request, OAuth2UserWrapper principal) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        redirectUri.ifPresent(uri -> {
            URI reUri = URI.create(uri);
            if (!clientAuthorizedUri.getHost()
                    .equalsIgnoreCase(reUri.getHost())) throw new BadRequestException("Unauthorized redirect URI");
        });

        String jwtToken = tokenProvider.createToken(principal);
        String redirectUrl = redirectUri.orElse(getDefaultTargetUrl());
        if (redirectUrl.contains("#")) {
            String[] splitHashUrl = redirectUrl.split("#");
            UriComponents fragmentUri = UriComponentsBuilder.fromUriString(splitHashUrl[1])
                    .queryParam("jwt", jwtToken)
                    .build();
            UriComponents uri = UriComponentsBuilder.fromUriString(splitHashUrl[0])
                    .fragment(fragmentUri.toUriString())
                    .build();
            return uri.toUriString();

        }
        return UriComponentsBuilder.fromUriString(redirectUrl)
                .queryParam("jwt", jwtToken)
                .build()
                .toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}
