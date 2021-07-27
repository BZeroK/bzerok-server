package com.bzerok.server.web;

import java.util.Collections;
import java.util.List;

import com.bzerok.server.config.security.jwt.JwtFilter;
import com.bzerok.server.config.security.jwt.TokenProvider;
import com.bzerok.server.domain.liquor.Liquor;
import com.bzerok.server.domain.liquor.LiquorRepository;
import com.bzerok.server.web.dto.LiquorSaveRequestDto;
import com.bzerok.server.web.dto.LiquorUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LiquorPostControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private LiquorRepository liquorRepository;

    @Autowired
    private TokenProvider tokenProvider;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .addFilters(new JwtFilter(tokenProvider))
                .build();

        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken("1", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))));
    }

    @After
    public void tearDown() {
        liquorRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void postLiquorPost() throws Exception {
        // given
        Long userId = 1L;
        String name = "name";
        Integer category = 1;
        Integer volume = 750;
        Integer price = 100000;
        Integer rate = 5;
        String picture = "picture";
        String etc = "etc";

        LiquorSaveRequestDto requestDto = LiquorSaveRequestDto.builder()
                .name(name)
                .category(category)
                .volume(volume)
                .price(price)
                .rate(rate)
                .picture(picture)
                .etc(etc)
                .build();
        requestDto.setUserIdFromSession(userId);

        String url = "http://localhost:" + port + "/api/v1/liquor";

        // when
        mvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(new ObjectMapper().writeValueAsString(requestDto))
            .sessionAttr("userId", 1L))
            .andExpect(status().isOk());

        // then
        List<Liquor> all = liquorRepository.findAll();
        assertThat(all.get(0).getUserId()).isEqualTo(userId);
        assertThat(all.get(0).getName()).isEqualTo(name);
        assertThat(all.get(0).getCategory()).isEqualTo(category);
        assertThat(all.get(0).getVolume()).isEqualTo(volume);
        assertThat(all.get(0).getPrice()).isEqualTo(price);
        assertThat(all.get(0).getRate()).isEqualTo(rate);
        assertThat(all.get(0).getPicture()).isEqualTo(picture);
        assertThat(all.get(0).getEtc()).isEqualTo(etc);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void updateLiquorPost() throws Exception {
        // given
        Liquor savedLiquorPost = liquorRepository.save(Liquor.builder()
                .userId(1L)
                .name("name")
                .category(1)
                .volume(750)
                .price(100000)
                .rate(5)
                .picture("picture")
                .etc("etc")
                .build());

        Long liquorPostId = savedLiquorPost.getLiquorPostId();
        String expectedName = "updated name";
        Integer expectedCategory = 2;
        String expectedEtc = "updated etc";

        LiquorUpdateRequestDto requestDto = LiquorUpdateRequestDto.builder()
                .name(expectedName)
                .category(expectedCategory)
                .etc(expectedEtc)
                .build();

        String url = "http://localhost:" + port + "/api/v1/liquor/" + liquorPostId;

        // when
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // then
        List<Liquor> all = liquorRepository.findAll();
        assertThat(all.get(0).getName()).isEqualTo(expectedName);
        assertThat(all.get(0).getCategory()).isEqualTo(expectedCategory);
        assertThat(all.get(0).getEtc()).isEqualTo(expectedEtc);
    }

}
