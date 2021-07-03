package com.bzerok.server.service.login;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bzerok.server.domain.login.SocialLogin;
import com.bzerok.server.domain.login.SocialLoginType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final List<SocialLogin> socialLoginList;
    private final HttpServletResponse response;

    public void redirectLoginRequest(SocialLoginType socialLoginType) {
        SocialLogin socialLogin = this.findSocialLoginByType(socialLoginType);
        String redirectURL = socialLogin.getRedirectURL();

        try {
            response.sendRedirect(redirectURL);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String requestAccessToken(SocialLoginType socialLoginType, String code) {
        SocialLogin socialLogin = this.findSocialLoginByType(socialLoginType);
        return socialLogin.requestAccessToken(code);
    }

    private SocialLogin findSocialLoginByType(SocialLoginType socialLoginType) {
        return socialLoginList.stream()
                .filter(x -> x.type() == socialLoginType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 소셜 로그인 방법입니다."));
    }

}
