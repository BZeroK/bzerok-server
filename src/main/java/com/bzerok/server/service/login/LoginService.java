package com.bzerok.server.service.login;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import com.bzerok.server.domain.login.SocialLogin;
import com.bzerok.server.domain.login.SocialLoginType;
import com.bzerok.server.domain.users.Users;
import com.bzerok.server.domain.users.UsersRepository;
import com.bzerok.server.web.dto.UserInfoDto;
import com.bzerok.server.web.dto.UserTokenDto;
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

    @Transactional
    public Long requestAccessToken(SocialLoginType socialLoginType, String code) {
        SocialLogin socialLogin = this.findSocialLoginByType(socialLoginType);
        String tokenResponse = socialLogin.requestAccessToken(code);

        try {
            UserTokenDto userTokenDto = objectMapper.readValue(tokenResponse, UserTokenDto.class);
            UserInfoDto userInfoDto = objectMapper.readValue(socialLogin.requestUserInfo(userTokenDto.getId_token()), UserInfoDto.class);

            Users users = usersRepository.findByEmail(userInfoDto.getEmail());

            if (users == null) {
                usersRepository.save(
                        Users.builder()
                        .email(userInfoDto.getEmail())
                        .name(userInfoDto.getName())
                        .token(userTokenDto.getAccess_token())
                        .idToken(userTokenDto.getId_token())
                        .build()
                        );

                users = usersRepository.findByEmail(userInfoDto.getEmail());
            }
            else {
                users.update(userTokenDto.getAccess_token(), userTokenDto.getId_token());
            }

            return users.getUserId();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private SocialLogin findSocialLoginByType(SocialLoginType socialLoginType) {
        return socialLoginList.stream()
                .filter(x -> x.type() == socialLoginType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 소셜 로그인 방법입니다."));
    }

}
