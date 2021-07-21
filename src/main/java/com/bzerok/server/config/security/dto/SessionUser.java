package com.bzerok.server.config.security.dto;

import java.io.Serializable;

import com.bzerok.server.domain.users.Users;
import lombok.Getter;

@Getter
public class SessionUser implements Serializable {

    private String name;
    private String email;

    public SessionUser(Users user) {
        this.name = user.getName();
        email = user.getEmail();
    }

}
