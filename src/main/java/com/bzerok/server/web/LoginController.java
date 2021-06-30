package com.bzerok.server.web;

import com.bzerok.server.domain.login.SocialLogin;
import com.bzerok.server.domain.login.SocialLoginType;
import com.bzerok.server.service.login.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/api/v1/login/{socialLoginType}")
    public void redirectLoginRequest(@PathVariable String socialLoginType) {
        log.info(">> 사용자로부터 SNS 로그인 요청을 받음 :: {} Social Login", socialLoginType);
        loginService.redirectLoginRequest(SocialLoginType.valueOf(socialLoginType.toUpperCase()));
    }

    @GetMapping("/api/v1/{socialLoginType}/callback")
    public String callback(@PathVariable String socialLoginType, @RequestParam String code) {
        log.info(">> 소셜 로그인 API 서버로부터 받은 code :: {}", code);
        return loginService.requestAccessToken(SocialLoginType.valueOf(socialLoginType.toUpperCase()), code);
    }

}