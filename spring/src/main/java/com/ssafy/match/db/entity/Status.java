package com.ssafy.match.db.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Status {
    진행, 모집, 종료;

    @JsonCreator
    public static Status from(String s) {
        return Status.valueOf(s.toUpperCase());
    }
}
