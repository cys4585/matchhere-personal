package com.ssafy.match.common.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum GroupAuthority {
    소유자, 관리자, 팀원;

    @JsonCreator
    public static ProjectProgressState from(String s) {
        return ProjectProgressState.valueOf(s.toUpperCase());
    }
}
