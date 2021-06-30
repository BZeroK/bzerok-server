package com.bzerok.server.web.dto;

import com.bzerok.server.domain.test.Test;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TestSaveRequestDto {

    private String name;
    private Integer age;

    @Builder
    public TestSaveRequestDto(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Test toEntity() {
        return Test.builder()
                .name(name)
                .age(age)
                .build();
    }

}
