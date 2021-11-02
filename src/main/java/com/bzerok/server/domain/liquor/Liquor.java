package com.bzerok.server.domain.liquor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.bzerok.server.domain.BaseTimeEntity;
import io.jsonwebtoken.lang.Assert;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Liquor extends BaseTimeEntity {

    @Id // PK field
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 생성 규칙, auto increment
    private Long liquorPostId;

    @Column(nullable = false) // 테이블의 컬럼을 나타낸다. 굳이 사용하지 않아도 되지만 기본값의 변경이 필요한 경우 사용한다.
    private Long userId;

    @Column(nullable = false)
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

    // Setter가 없기 때문에 생성자를 통해 최종값을 채운 후 DB에 삽입하는 것을 기본구조로 한다.
    // 값 변경이 필요한 경우 해당 이벤트에 맞는 public 메소드를 호출하여 변경하는 것을 전제로 한다.

    @Builder
    public Liquor(Long userId, String name, Integer category, Integer volume, Integer price, Integer rate, String picture, String etc) {
        Assert.notNull(userId, "User id must not be null");
        Assert.notNull(name, "Liquor name must not be null");

        this.userId = userId;
        this.name = name;
        this.category = category;
        this.volume = volume;
        this.price = price;
        this.rate = rate;
        this.picture = picture;
        this.etc = etc;
    }

    public void update(String name, Integer category, Integer volume, Integer price, Integer rate, String picture, String etc) {
        Assert.notNull(name, "Liquor name must not be null");

        this.name = name;
        this.category = category;
        this.volume = volume;
        this.price = price;
        this.rate = rate;
        this.picture = picture;
        this.etc = etc;
    }
}
