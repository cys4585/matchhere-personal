package com.ssafy.match.common.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PublicScope {
    Public("전체 공개"), ClubOnly("클럽 멤버에게만 공개"), Private("비공개");

    private final String state;

    PublicScope(String state) {
        this.state = state;
    }

    public String getState(){
        return this.state;
    }

    @JsonCreator
    public static PublicScope from(String s) {
        PublicScope publicScope;
        if(s.equals(Public.state)){
            publicScope = Public;
        }else if(s.equals(ClubOnly.state)){
            publicScope = ClubOnly;
        }else {
            publicScope = Private;
        }
        return publicScope;
    }

}
