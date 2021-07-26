package com.bzerok.server.config.security.oauth2.dto;

import java.io.Serializable;

import com.bzerok.server.domain.users.Role;
import com.bzerok.server.domain.users.Users;
import lombok.Getter;

@Getter
public class SessionUser implements Serializable {

    private Long userId;
    private Role role;

    public SessionUser(Users user) {
        this.userId = user.getUserId();
        this.role = user.getRole();
    }

}
