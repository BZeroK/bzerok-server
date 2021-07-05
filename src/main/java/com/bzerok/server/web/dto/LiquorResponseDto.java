package com.bzerok.server.web.dto;

import com.bzerok.server.domain.liquor.Liquor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LiquorResponseDto {

    private Long liquorPostId;
    private Long userId;
    private String name;
    private Integer category;
    private Integer volume;
    private Integer price;
    private Integer rate;
    private String picture;
    private String etc;

    @Builder
    public LiquorResponseDto(Liquor entity) {
        this.liquorPostId = entity.getLiquorPostId();
        this.userId = entity.getUserId();
        this.name = entity.getName();
        this.category = entity.getCategory();
        this.volume = entity.getVolume();
        this.price = entity.getPrice();
        this.rate = entity.getRate();
        this.picture = entity.getPicture();
        this.etc = entity.getEtc();
    }

}
