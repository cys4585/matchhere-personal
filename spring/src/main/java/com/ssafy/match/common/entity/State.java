package com.ssafy.match.common.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum State {
    졸업, 재학, 중퇴, 수료;

    @JsonCreator
    public static State from(String s) {
        return State.valueOf(s.toUpperCase());
    }
}
