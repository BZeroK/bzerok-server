package com.bzerok.server.security;

import javax.servlet.http.Cookie;

import com.bzerok.server.config.security.jwt.TokenProvider;
import com.bzerok.server.domain.liquor.LiquorRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecurityTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private LiquorRepository liquorRepository;

    private MockMvc mvc;

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private long expiration;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @After
    public void tearDown() {
        liquorRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "GUEST")
    public void accessWithoutAuthorityByGuest() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder =
                get("/api/v1/liquor");

        // then
        mvc.perform(requestBuilder)
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void accessWithoutAuthorityByUser() throws Exception {

    }

    @Test
    @WithMockUser(roles = "GUEST")
    public void accessWithAuthorityByGuest() throws Exception {
        Long userId = 1L;
        String userEmail = "test@test.com";
        String token = new TokenProvider().createToken(userId, userEmail);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void accessWithAuthorityByUser() throws Exception {

    }

}
