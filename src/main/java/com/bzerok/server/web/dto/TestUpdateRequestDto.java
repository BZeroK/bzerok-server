package com.bzerok.server.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TestUpdateRequestDto {

    private String name;
    private Integer age;

    @Builder
    public TestUpdateRequestDto(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

}
