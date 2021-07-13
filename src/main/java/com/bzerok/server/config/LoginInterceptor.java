package com.bzerok.server.config;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Master 권한으로 접근하는 경우
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userId")) {
                    request.getSession().setAttribute("userId", Long.parseLong(cookie.getValue()));
                    return true;
                }
            }
        }

        // 일반 권한으로 접근하는 경우
        Long userId = (Long)request.getSession().getAttribute("userId");
        log.info("userId :: {}", userId);

        if (userId == null) {
            response.sendRedirect("http://localhost:3000");
            return false;
        }
        return true;
    }

}
