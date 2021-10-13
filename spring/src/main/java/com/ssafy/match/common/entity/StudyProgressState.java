package com.ssafy.match.common.entity;

public enum StudyProgressState {
    Ready("스터디 준비 중"), Progress("스터디 진행 중"), Finish("스터디 종료");

    private final String state;

    StudyProgressState(String state) {
        this.state = state;
    }

    public String getState(){
        return this.state;
    }

    public static StudyProgressState from(String s) {
        StudyProgressState projectProgressState;
        if(s.equals(Ready.state)){
            projectProgressState = Ready;
        }else if(s.equals(Progress.state)){
            projectProgressState = Progress;
        }else {
            projectProgressState = Finish;
        }
        return projectProgressState;
    }

}

