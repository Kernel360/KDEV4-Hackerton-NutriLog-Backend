package com.nutrilog.nutrilog_backend.common.entities;

public enum UserType {
    KAKAO("kakao"), GOOGLE("google"), GUEST("guest");

    String value;

    UserType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
