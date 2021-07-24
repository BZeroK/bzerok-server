package com.bzerok.server.config.security.oauth2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bzerok.server.utils.CookieUtils;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

@Component
public class HttpCookieOAuth2AuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    @Value("${app.cookie.oauth2AuthorizationRequestName}")
    private String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME;
    @Value("${app.cookie.redirectUriName}")
    private String REDIRECT_URI_COOKIE_NAME;
    @Value("${app.cookie.expireSeconds}")
    private Integer COOKIE_EXPIRE_SECONDS;

    private final Logger logger = LoggerFactory.getLogger(HttpCookieOAuth2AuthorizationRequestRepository.class);

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        logger.debug(">> loadAuthorizationRequest");
        return CookieUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
                .map(cookie -> CookieUtils.deserialize(cookie, OAuth2AuthorizationRequest.class))
                .orElse(null);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        logger.debug(">> saveAuthorizationRequest");

        if (authorizationRequest == null) {
            removeAuthorizationRequestCookies(request, response);
            return;
        }

        CookieUtils.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, CookieUtils.serialize(authorizationRequest), COOKIE_EXPIRE_SECONDS);
        String redirectUri = request.getParameter(REDIRECT_URI_COOKIE_NAME);

        if (StringUtils.isNotBlank(redirectUri)) {
            CookieUtils.addCookie(response, REDIRECT_URI_COOKIE_NAME, redirectUri, COOKIE_EXPIRE_SECONDS);
        }
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
        logger.debug(">> removeAuthorizationRequest");
        return this.loadAuthorizationRequest(request);
    }

    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        CookieUtils.deleteCookie(request, response, REDIRECT_URI_COOKIE_NAME);
    }

}
