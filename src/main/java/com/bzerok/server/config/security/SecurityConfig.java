package com.bzerok.server.config.security;

import com.bzerok.server.domain.users.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final HttpCookieOAuth2AuthorizationRequestRepository cookieOAuth2AuthorizationRequestRepository;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .csrf()
                    .disable()
                .formLogin()
                    .disable()
                .httpBasic()
                    .disable()
                .headers().frameOptions().disable()
                    .and()
                // URL 권한 관리의 시작점
                .authorizeRequests()
                    .antMatchers("/h2-console/**", "/api/v1/login/**")
                        .permitAll()
                    .antMatchers("/api/v1/**")
                        .hasRole(Role.USER.name())
                    .anyRequest()
                        .authenticated()
                    .and()
                .logout()
                    .logoutSuccessUrl("http://localhost:3000")
                    .invalidateHttpSession(true)
                    .and()
                .oauth2Login()
                    .authorizationEndpoint()
                        .baseUri("/api/v1/login")
                        .authorizationRequestRepository(cookieOAuth2AuthorizationRequestRepository)
                        .and()
                    .redirectionEndpoint()
                        .baseUri("/api/v1/login/callback/google")
                        .and()
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService)
                        .and()
                    .successHandler(oAuth2AuthenticationSuccessHandler);
    }

}
