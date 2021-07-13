package com.bzerok.server.domain.login;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GoogleLogin implements SocialLogin {

    @Value("${oauth.google.url}")
    private String GOOGLE_SNS_BASE_URL;
    @Value("${oauth.google.client-id}")
    private String GOOGLE_SNS_CLIENT_ID;
    @Value("${oauth.google.callback-url}")
    private String GOOGLE_SNS_CALLBACK_URL;
    @Value("${oauth.google.client-secret}")
    private String GOOGLE_SNS_CLIENT_SECRET;
    @Value("${oauth.google.token-url}")
    private String GOOGLE_SNS_TOKEN_BASE_URL;

    @Override
    public String getRedirectURL() {
        Map<String, Object> params = new HashMap<>();

        params.put("scope", "profile email");
        params.put("response_type", "code");
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);

        String paramsString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return GOOGLE_SNS_BASE_URL + "?" + paramsString;
    }

    @Override
    public String requestAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("client_secret", GOOGLE_SNS_CLIENT_SECRET);
        params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);
        params.put("grant_type", "authorization_code");

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(GOOGLE_SNS_TOKEN_BASE_URL, params, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK)
            return responseEntity.getBody();
        else
            return null;
    }

    @Override
    public String requestUserInfo(String idToken) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK)
            return responseEntity.getBody();
        else
            return null;
    }

}
