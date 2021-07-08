package com.bzerok.server.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoDto {

    private String email;
    private String name;
    private String access_token;
    private String id_token;



}
