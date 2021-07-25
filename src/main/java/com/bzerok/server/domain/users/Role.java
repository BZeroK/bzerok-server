package com.bzerok.server.domain.users;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "방문자"),
    USER("ROLE_USER", "사용자");

    private final String key;
    private final String title;
}
