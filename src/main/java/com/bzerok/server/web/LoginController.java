package com.bzerok.server.web;

import com.bzerok.server.domain.login.SocialLoginType;
import com.bzerok.server.service.login.LoginService;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/api/v1/login/{socialLoginType}")
    public String redirectLoginRequest(@PathVariable String socialLoginType) {
        log.info(">> 사용자로부터 SNS 로그인 요청을 받음 :: {} Social Login", socialLoginType);

        String redirectURL = loginService.redirectLoginRequest(SocialLoginType.valueOf(socialLoginType.toUpperCase()));
        JsonObject response = new JsonObject();

        response.addProperty("code", 200);
        response.addProperty("message", socialLoginType + " Redirect URL");
        response.addProperty("data", redirectURL);

        return response.toString();
    }

    @GetMapping("/api/v1/{socialLoginType}/callback")
    public void callback(@PathVariable String socialLoginType, @RequestParam String code) {
        log.info(">> 소셜 로그인 API 서버로부터 받은 code :: {}", code);
        loginService.requestAccessToken(SocialLoginType.valueOf(socialLoginType.toUpperCase()), code);
    }

}
