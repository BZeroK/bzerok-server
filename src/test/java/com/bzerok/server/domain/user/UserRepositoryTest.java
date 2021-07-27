package com.bzerok.server.domain.user;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @After
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    public void enroll() {
        // Given
        String name = "홍길동";
        String email = "test@test.com";
        String picture = "picture";
        Role role = Role.USER;

        userRepository.save(User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(role)
                .build());

        // When
        List<User> userList = userRepository.findAll();

        // Then
        User user = userList.get(0);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getPicture()).isEqualTo(picture);
        assertThat(user.getRole()).isEqualTo(role);
    }

    @Test
    public void update() {
        // Given
        String name = "홍길동";
        String email = "test@test.com";
        String picture = "picture";
        Role role = Role.USER;

        String expectedName = "김길동";
        String expectedPicture = "updated picture";

        User user = userRepository.save(User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(role)
                .build());

        // When
        user.update(expectedName, expectedPicture);

        // Then
        assertThat(user.getName()).isEqualTo(expectedName);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getPicture()).isEqualTo(expectedPicture);
        assertThat(user.getRole()).isEqualTo(role);
    }

}
