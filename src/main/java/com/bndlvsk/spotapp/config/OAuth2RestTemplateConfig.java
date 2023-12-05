package com.bndlvsk.spotapp.config;

import com.bndlvsk.spotapp.interceptor.HeaderModifierTokenRefresherInterceptor;
import com.bndlvsk.spotapp.service.SpotifyTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OAuth2RestTemplateConfig {

    // checking if token needs to be refreshed
    // response status code = 401 - send request to refresh token -> re-authenticate -> repeat


    @Bean("restTemplateInterceptor")
    public RestTemplate restTemplateInterceptor(HeaderModifierTokenRefresherInterceptor interceptor) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(interceptor);
        return restTemplate;
    }

    @Bean("restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public HeaderModifierTokenRefresherInterceptor interceptor(OAuth2AuthorizedClientService clientService,
                                                               SpotifyTokenService tokenService) {
        return new HeaderModifierTokenRefresherInterceptor(clientService, tokenService);
    }

}
