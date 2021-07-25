package com.bzerok.server.config.security.oauth2;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bzerok.server.config.security.jwt.TokenProvider;
import com.bzerok.server.utils.CookieUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${app.oauth2.authorizedRedirectUris}")
    private List<String> AUTHORIZED_REDIRECT_URIS;
    @Value("${app.cookie.jwtTokenName}")
    private String JWT_TOKEN_NAME;
    @Value("${app.cookie.redirectUriName}")
    private String REDIRECT_URI_COOKIE_NAME;
    @Value("${app.session.attributes.user}")
    private String SESSION_USER;

    private final HttpSession httpSession;
    private final TokenProvider tokenProvider;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private static final Logger logger = LoggerFactory.getLogger(OAuth2AuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.info(">> Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);

        String token = tokenProvider.createToken(authentication);
        CookieUtils.addCookie(response, JWT_TOKEN_NAME, token, 846000);

        logger.debug(">> Redirect URI :: {}", targetUrl);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_COOKIE_NAME)
                .map(Cookie::getValue);

        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            logger.info(">> Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
            return "";
        }

        return UriComponentsBuilder.fromUriString(redirectUri.orElse(getDefaultTargetUrl()))
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return AUTHORIZED_REDIRECT_URIS
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    return authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort();
                });
    }

}
