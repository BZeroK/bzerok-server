package com.bzerok.server.domain.liquor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.bzerok.server.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Liquor extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long liquorPostId;

    @Column
    private Long userId;

    @Column
    private String name;

    @Column
    private Integer category;

    @Column
    private Integer volume;

    @Column
    private Integer price;

    @Column
    private Integer rate;

    @Column
    private String picture;

    @Column
    private String etc;

    @Builder
    public Liquor(Long userId, String name, Integer category ,Integer volume, Integer price, Integer rate, String picture, String etc) {
        this.userId = userId;
        this.name = name;
        this.category = category;
        this.volume = volume;
        this.price = price;
        this.rate = rate;
        this.picture = picture;
        this.etc = etc;
    }

    public void update(String name, Integer category ,Integer volume, Integer price, Integer rate, String picture, String etc) {
        this.name = name;
        this.category = category;
        this.volume = volume;
        this.price = price;
        this.rate = rate;
        this.picture = picture;
        this.etc = etc;
    }

}
