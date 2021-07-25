package com.bzerok.server.config.security.oauth2;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bzerok.server.utils.CookieUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Value("${app.cookie.redirectUriName}")
    private String REDIRECT_URI_COOKIE_NAME;

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final Logger logger = LoggerFactory.getLogger(OAuth2AuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure (HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, SecurityException {
        String targetUrl = CookieUtils.getCookie(request, REDIRECT_URI_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse(("/"));

        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", exception.getLocalizedMessage())
                .build().toUriString();

        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);

        logger.info(">> Authentication Failure!, Redirect URI :: {}", targetUrl);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

}
