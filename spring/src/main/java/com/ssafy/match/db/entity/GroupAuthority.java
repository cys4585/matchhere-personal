package com.ssafy.match.db.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum GroupAuthority {
    소유자, 관리자, 팀원;

    @JsonCreator
    public static Status from(String s) {
        return Status.valueOf(s.toUpperCase());
    }
}
