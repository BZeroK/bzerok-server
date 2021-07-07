package com.bzerok.server.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoDto {

    private String access_token;
    private String id_token;
    private Integer expires_in;
    private String token_type;
    private String scope;

}
