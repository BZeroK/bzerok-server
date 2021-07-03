package com.bzerok.server.web.dto;

import com.bzerok.server.domain.liquor.Liquor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LiquorSaveRequestDto {

    private Long userId;
    private String name;
    private Integer category;
    private Integer volume;
    private Integer price;
    private Integer rate;
    private String picture;
    private String etc;

    @Builder
    public LiquorSaveRequestDto(Liquor entity) {
        this.userId = entity.getUserId();
        this.name = entity.getName();
        this.category = entity.getCategory();
        this.volume = entity.getVolume();
        this.price = entity.getPrice();
        this.rate = entity.getRate();
        this.picture = entity.getPicture();
        this.etc = entity.getEtc();
    }

    public Liquor toEntity() {
        return Liquor.builder()
                .userId(userId)
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
