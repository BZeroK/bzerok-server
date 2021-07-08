package com.bzerok.server.domain.login;

public interface SocialLogin {
    String getRedirectURL();

    String requestAccessToken(String code);

    String requestUserInfo(String idToken);

    default SocialLoginType type() {
        if (this instanceof GoogleLogin) return SocialLoginType.GOOGLE;
        else return null;
    }
}
