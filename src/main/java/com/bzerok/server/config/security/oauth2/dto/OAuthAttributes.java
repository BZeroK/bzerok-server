package com.bzerok.server.config.security.oauth2.dto;

import java.util.Map;

import com.bzerok.server.domain.user.Role;
import com.bzerok.server.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);

        // TODO
//        else if (registrationId.equals("kakao")) {
//            return ofKakao(userNameAttributeName, attributes);
//        }
//        else if (registrationId.equals("naver")) {
//            return ofNaver(userNameAttributeName, attributes);
//        }
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
            .name((String) attributes.get("name"))
            .email((String) attributes.get("email"))
            .picture((String) attributes.get("picture"))
            .attributes(attributes)
            .nameAttributeKey(userNameAttributeName)
            .build();
    }

//    public static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
//        // TODO
//    }
//
//    public static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
//        // TODO
//    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.USER)
                .build();
    }



}
