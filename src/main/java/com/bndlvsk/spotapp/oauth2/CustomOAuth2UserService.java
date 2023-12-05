package com.bndlvsk.spotapp.oauth2;

import com.bndlvsk.spotapp.DTO.UserDTO;
import com.bndlvsk.spotapp.entity.User;
import com.bndlvsk.spotapp.entity.UserInfo;
import com.bndlvsk.spotapp.entity.UserStats;
import com.bndlvsk.spotapp.exception.UserAuthenticationException;
import com.bndlvsk.spotapp.exception.UserNotFoundException;
import com.bndlvsk.spotapp.model.spotify.Image;
import com.bndlvsk.spotapp.model.OAuth2UserWrapper;
import com.bndlvsk.spotapp.service.BetaUserService;
import com.bndlvsk.spotapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final BetaUserService betaUserService;
    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService defaultOAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = defaultOAuth2UserService.loadUser(userRequest);
        return getUserOrCreate(new OAuth2UserWrapper(oAuth2User));
    }

    public OAuth2User getUserOrCreate(OAuth2UserWrapper oAuth2User) {
        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        try {
            String email = oAuth2User.getEmail();
            UserDTO userDTO = Optional.ofNullable(userService.findUserByEmail(email))
                    .orElseGet(() -> userService.saveUser(
                            new User().withId(0)
                                    .withExternalId(oAuth2User.getId())
                                    .withUsername(oAuth2User.getDisplayName())
                                    .withEmail(email)
                                    .withImage(getImageUrl(oAuth2User.getImages()))
                                    .withJoinDate(LocalDateTime.now())
                                    .withUserStats(new UserStats())
                                    .withUserInfo(new UserInfo())));
            if (!betaUserService.findBetaUserByEmail(userDTO.getEmail()).isActive())
                throw new UserAuthenticationException("User not registered for beta");
            attributes.put("userId", Long.parseLong(userDTO.getId()));
            return new OAuth2UserWrapper(new DefaultOAuth2User(oAuth2User.getAuthorities(),
                    Collections.unmodifiableMap(attributes),
                    "display_name"));
        } catch (NullPointerException | UserNotFoundException e) {
            throw new UserAuthenticationException("User cannot be properly authenticated");
        }
    }

    private String getImageUrl(List<Image> images) throws NullPointerException {
        return images.stream()
                .max(Comparator.comparing(Image::getHeight))
                .map(Image::getUrl)
                .orElse("./account.png");
    }


}
