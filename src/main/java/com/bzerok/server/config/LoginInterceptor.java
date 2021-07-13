package com.bzerok.server.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // preflight 요청 승인
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        Long userId = (Long) request.getSession().getAttribute("userId");
        log.info("userId :: {}", userId);

        if (userId == null) {
            JsonObject res = new JsonObject();
            res.addProperty("code", 403);
            res.addProperty("message", "로그인이 필요한 서비스입니다.");

            response.setContentType(request.getContentType());
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().append(res.toString());
            return false;
        }
        else {
            return true;
        }
    }

}
