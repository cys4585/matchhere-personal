package com.ssafy.match.common.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Level {
    상("상"),
    중("중"),
    하("하");

    String value;
    Level(String value) { this.value = value; }
    public String value() { return value; }

    @JsonCreator
    public static Level from(String s) {
        return Level.valueOf(s);
    }
}
