package com.ssafy.match.common.entity;

public enum RecruitmentState {
    Recruiting("모집 중"), Finish("모집 마감");

    private final String state;

    RecruitmentState(String state) {
        this.state = state;
    }

    public String getState(){
        return this.state;
    }

    public static RecruitmentState from(String s) {
        RecruitmentState recruitmentState;
        if(s.equals(Recruiting.state)){
            recruitmentState = Recruiting;
        }else {
            recruitmentState = Finish;
        }
        return recruitmentState;
    }

}
