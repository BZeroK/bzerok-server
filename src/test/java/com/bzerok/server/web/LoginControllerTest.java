package com.bzerok.server.web;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void requestGoogleLogin() throws Exception {
        String expectedRedirectURL = "https://accounts.google.com/o/oauth2/v2/auth?scope=profile email&response_type=code&redirect_uri=http://localhost:8080/api/v1/google/callback&client_id=144032148668-nl0a3ivri6afsqkplh2s25vakn7ofsk5.apps.googleusercontent.com";
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/login/google", String.class);
        Map<String, String> responseBody = new ObjectMapper().readValue(response.getBody(), Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseBody.get("data")).isEqualTo(expectedRedirectURL);
    }

}
