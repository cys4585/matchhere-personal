package com.ssafy.match.common.entity;

public enum ProjectProgressState {
    Ready("프로젝트 준비 중"), Progress("프로젝트 진행 중"), Finish("프로젝트 종료");

    private final String state;

    ProjectProgressState(String state) {
        this.state = state;
    }

    public String getState(){
        return this.state;
    }

    public static ProjectProgressState from(String s) {
        ProjectProgressState projectProgressState;
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

