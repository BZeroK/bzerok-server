package com.bzerok.server.domain.liquor;

import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LiquorRepositoryTest {

    @Autowired
    LiquorRepository liquorRepository;

    @After
    public void cleanup() {
        liquorRepository.deleteAll();
    }

    @Test
    public void findByUserId() {
        // Given
        Long userId = 1L;
        String name = "name";
        Integer category = 1;
        Integer volume = 750;
        Integer price = 100000;
        Integer rate = 5;
        String picture = "picture";
        String etc = "etc";

        liquorRepository.save(Liquor.builder()
                .userId(userId)
                .name(name)
                .category(category)
                .volume(volume)
                .price(price)
                .rate(rate)
                .picture(picture)
                .etc(etc)
                .build());

        // When
        List<Liquor> liquorList = liquorRepository.findByUserId(userId);

        // Then
        Liquor liquor = liquorList.get(0);
        assertThat(liquor.getUserId()).isEqualTo(userId);
        assertThat(liquor.getName()).isEqualTo(name);
        assertThat(liquor.getCategory()).isEqualTo(category);
        assertThat(liquor.getVolume()).isEqualTo(volume);
        assertThat(liquor.getPrice()).isEqualTo(price);
        assertThat(liquor.getRate()).isEqualTo(rate);
        assertThat(liquor.getPicture()).isEqualTo(picture);
        assertThat(liquor.getEtc()).isEqualTo(etc);
    }

    @Test
    public void update() {
        // Given
        Long userId = 1L;
        String name = "name";
        Integer category = 1;
        Integer volume = 750;
        Integer price = 100000;
        Integer rate = 5;
        String picture = "picture";
        String etc = "etc";

        Liquor savedLiquorPost = liquorRepository.save(Liquor.builder()
                .userId(userId)
                .name(name)
                .category(category)
                .volume(volume)
                .price(price)
                .rate(rate)
                .picture(picture)
                .etc(etc)
                .build());

        // When
        String expectedName = "updated name";
        Integer expectedCategory = 2;
        String expectedEtc = "updated etc";

        savedLiquorPost.update(expectedName, expectedCategory, volume, price, rate, picture, expectedEtc);

        // Then
        assertThat(savedLiquorPost).isNotNull();
        assertThat(savedLiquorPost.getUserId()).isEqualTo(userId);
        assertThat(savedLiquorPost.getName()).isEqualTo(expectedName);
        assertThat(savedLiquorPost.getCategory()).isEqualTo(expectedCategory);
        assertThat(savedLiquorPost.getVolume()).isEqualTo(volume);
        assertThat(savedLiquorPost.getPrice()).isEqualTo(price);
        assertThat(savedLiquorPost.getRate()).isEqualTo(rate);
        assertThat(savedLiquorPost.getPicture()).isEqualTo(picture);
        assertThat(savedLiquorPost.getEtc()).isEqualTo(expectedEtc);
    }

    @Test
    public void delete() {
        // Given
        Long userId = 1L;
        String name = "name";
        Integer category = 1;
        Integer volume = 750;
        Integer price = 100000;
        Integer rate = 5;
        String picture = "picture";
        String etc = "etc";

        Liquor savedLiquor = liquorRepository.save(Liquor.builder()
                .userId(userId)
                .name(name)
                .category(category)
                .volume(volume)
                .price(price)
                .rate(rate)
                .picture(picture)
                .etc(etc)
                .build());

        // When
        liquorRepository.deleteById(savedLiquor.getLiquorPostId());

        // Then
        Optional<Liquor> liquor = liquorRepository.findById(savedLiquor.getLiquorPostId());
        assertThat(liquor.isPresent()).isEqualTo(false);
    }

}
