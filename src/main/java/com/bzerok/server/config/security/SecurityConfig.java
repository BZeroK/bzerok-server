package com.bzerok.server.config.security;

import com.bzerok.server.config.security.jwt.JwtAccessDeniedHandler;
import com.bzerok.server.config.security.jwt.JwtAuthenticationEntryPoint;
import com.bzerok.server.config.security.jwt.JwtFilter;
import com.bzerok.server.config.security.jwt.TokenProvider;
import com.bzerok.server.config.security.oauth2.CustomOAuth2UserService;
import com.bzerok.server.config.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.bzerok.server.config.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.bzerok.server.config.security.oauth2.OAuth2AuthenticationSuccessHandler;
import com.bzerok.server.domain.users.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final HttpCookieOAuth2AuthorizationRequestRepository cookieOAuth2AuthorizationRequestRepository;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .headers().frameOptions().disable()

                // URL 권한 관리
                .and()
                .authorizeRequests()
                    .antMatchers("/h2-console/**",
                            "/api/v1/oauth2/**"
                        ).permitAll()
                    .antMatchers("api/v1/admin/**")
                        .hasRole(Role.ADMIN.name())
                    .antMatchers("/api/v1/**")
                        .hasRole(Role.USER.name())
                    .anyRequest()
                        .authenticated()

                // OAUTH 2.0
                .and()
                .oauth2Login()
                    .authorizationEndpoint()
                        .baseUri("/api/v1/oauth2/authorize")
                        .authorizationRequestRepository(cookieOAuth2AuthorizationRequestRepository)
                        .and()
                    .redirectionEndpoint()
                        .baseUri("/api/v1/oauth2/callback/*")
                        .and()
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService)
                        .and()
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler(oAuth2AuthenticationFailureHandler)

                .and()
                .logout()
                    .logoutUrl("/api/v1/oauth2/logout")
                    .deleteCookies("bzerok_token")
                    .permitAll();

        http.addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
    }

}
