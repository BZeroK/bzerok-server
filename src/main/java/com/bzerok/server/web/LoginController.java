package com.bzerok.server.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public void callback(HttpServletRequest request, HttpServletResponse response, @PathVariable String socialLoginType, @RequestParam String code) throws Exception {
        log.info(">> 소셜 로그인 API 서버로부터 받은 code :: {}", code);
        Long userId = loginService.requestAccessToken(SocialLoginType.valueOf(socialLoginType.toUpperCase()), code);
        JsonObject res = new JsonObject();

        log.info("request :: {}", request.toString());

        if (userId != null) {
            res.addProperty("code", 200);
            res.addProperty("message", "로그인 성공");

            request.getSession().setAttribute("userId", userId);
//            response.sendRedirect("http://localhost:3000/");
        }
        else {
            res.addProperty("code", 500);
            res.addProperty("message", "서버 오류로 인한 로그인 실패");
        }

        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().append(res.toString());
        response.sendRedirect("http://localhost:3000");
    }

}
