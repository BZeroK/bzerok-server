package com.bzerok.server.web.dto;

import com.bzerok.server.domain.liquor.Liquor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LiquorUpdateRequestDto {

    private String name;
    private Integer category;
    private Integer volume;
    private Integer price;
    private Integer rate;
    private String picture;
    private String etc;

    @Builder
    public LiquorUpdateRequestDto(String name, Integer category, Integer volume, Integer price, Integer rate, String picture, String etc) {
        this.name = name;
        this.category = category;
        this.volume = volume;
        this.price = price;
        this.rate = rate;
        this.picture = picture;
        this.etc = etc;
    }

    public Liquor toEntity() {
        return Liquor.builder()
                .name(name)
                .category(category)
                .volume(volume)
                .price(price)
                .rate(rate)
                .picture(picture)
                .etc(etc)
                .build();
    }

}
