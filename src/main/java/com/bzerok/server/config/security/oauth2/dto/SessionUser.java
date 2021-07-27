package com.bzerok.server.config.security.oauth2.dto;

import java.io.Serializable;

import com.bzerok.server.domain.user.Role;
import com.bzerok.server.domain.user.User;
import lombok.Getter;

@Getter
public class SessionUser implements Serializable {

    private Long userId;
    private Role role;

    public SessionUser(User user) {
        this.userId = user.getUserId();
        this.role = user.getRole();
    }

}
