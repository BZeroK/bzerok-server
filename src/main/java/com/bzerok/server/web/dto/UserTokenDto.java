package com.bzerok.server.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserTokenDto {

    private String access_token;
    private String id_token;

}
