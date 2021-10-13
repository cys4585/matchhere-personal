package com.ssafy.match.common.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum City {
    서울, 부산, 대구, 인천, 광주, 대전, 울산, 세종, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 온라인, 무관;

    @JsonCreator
    public static City from(String s) {
        return City.valueOf(s.toUpperCase());
    }
}
