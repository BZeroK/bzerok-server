package com.bzerok.server.service.login;

import java.util.List;
import java.util.Map;

import com.bzerok.server.domain.login.SocialLogin;
import com.bzerok.server.domain.login.SocialLoginType;
import com.bzerok.server.domain.users.Users;
import com.bzerok.server.domain.users.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginService {

    private final List<SocialLogin> socialLoginList;
    private final UsersRepository usersRepository;

    public String redirectLoginRequest(SocialLoginType socialLoginType) {
        SocialLogin socialLogin = this.findSocialLoginByType(socialLoginType);
        return socialLogin.getRedirectURL();
    }

    @Transactional
    public Long requestAccessToken(SocialLoginType socialLoginType, String code) {
        SocialLogin socialLogin = this.findSocialLoginByType(socialLoginType);
        String tokenResponse = socialLogin.requestAccessToken(code);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Map<String, String> userTokenData = objectMapper.readValue(tokenResponse, Map.class);
            Map<String, String> userInfoData = objectMapper.readValue(socialLogin.requestUserInfo(userTokenData.get("id_token")),  Map.class);
            Users users = usersRepository.findByEmail(userInfoData.get("email"));

            if (users == null) {
                usersRepository.save(
                        Users.builder()
                        .email(userInfoData.get("email"))
                        .name(userInfoData.get("name"))
                        .token(userTokenData.get("access_token"))
                        .idToken(userTokenData.get("id_token"))
                        .build()
                        );
                users = usersRepository.findByEmail(userInfoData.get("email"));
            }
            else {
                users.update(userTokenData.get("access_token"), userTokenData.get("id_token"));
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
