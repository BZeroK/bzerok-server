package com.bzerok.server.domain.users;

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
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column
    private String email;

    @Column
    private String name;

    @Column
    private String token;

    @Column(columnDefinition = "LONGTEXT")
    private String idToken;

    @Builder
    public Users (String email, String name, String token, String idToken) {
        this.email = email;
        this.name = name;
        this.token = token;
        this.idToken = idToken;
    }

    public void update(String token, String idToken) {
        this.token = token;
        this.idToken = idToken;
    }

}
