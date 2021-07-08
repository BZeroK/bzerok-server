package com.bzerok.server.config;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long userId = (Long)request.getSession().getAttribute("userId");

        log.info("userId :: {}", userId);

        // 로그인 되어 있지 않은 경우
        if (userId == null) {
            response.sendRedirect("http://localhost:3000");
            return false;
        }

        // 로그인 되어 있는 경우
        return true;
    }

}
