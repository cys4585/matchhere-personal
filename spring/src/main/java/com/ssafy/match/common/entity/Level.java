package com.ssafy.match.common.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Level {
    상, 중, 하;

    @JsonCreator
    public static Level from(String s) {
        return Level.valueOf(s);
    }
}
