package com.bzerok.server.service.login;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bzerok.server.domain.login.SocialLogin;
import com.bzerok.server.domain.login.SocialLoginType;
import com.bzerok.server.domain.users.UsersRepository;
import com.bzerok.server.web.dto.UserInfoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginService {

    private final List<SocialLogin> socialLoginList;
    private final UsersRepository usersRepository;
    private final HttpServletResponse response;
    private final ObjectMapper objectMapper;

    public String redirectLoginRequest(SocialLoginType socialLoginType) {
        SocialLogin socialLogin = this.findSocialLoginByType(socialLoginType);
        return socialLogin.getRedirectURL();
    }

    public void requestAccessToken(SocialLoginType socialLoginType, String code) {
        SocialLogin socialLogin = this.findSocialLoginByType(socialLoginType);
        String tokenResponse = socialLogin.requestAccessToken(code);

        log.info(">> Token 정보 :: {}", tokenResponse);

        try {
            UserInfoDto userInfoDto = objectMapper.readValue(tokenResponse, UserInfoDto.class);

            log.info(">> Token 정보 :: {}", userInfoDto.getAccess_token());
            log.info(">> Token 정보 :: {}", userInfoDto.getId_token());
            log.info(">> Token 정보 :: {}", userInfoDto.getScope());
            log.info(">> Token 정보 :: {}", userInfoDto.getExpires_in());
            log.info(">> Token 정보 :: {}", userInfoDto.getToken_type());



            response.sendRedirect("http://localhost:3000/");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SocialLogin findSocialLoginByType(SocialLoginType socialLoginType) {
        return socialLoginList.stream()
                .filter(x -> x.type() == socialLoginType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 소셜 로그인 방법입니다."));
    }

}
