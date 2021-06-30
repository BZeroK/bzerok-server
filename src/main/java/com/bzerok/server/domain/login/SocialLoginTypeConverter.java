package com.bzerok.server.domain.login;

import org.springframework.core.convert.converter.Converter;

public class SocialLoginTypeConverter implements Converter<String, SocialLoginType> {

    @Override
    public SocialLoginType convert(String s) {
        return SocialLoginType.valueOf(s.toUpperCase());
    }

}
