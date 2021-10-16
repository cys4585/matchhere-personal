package com.ssafy.match.member.dto;

import java.time.LocalDateTime;

public interface EducationInterface {
    Long getId();
    String getInstitution();
    String getDegree();
    String getMajor();
    LocalDateTime getStart_date();
    LocalDateTime getEnd_date();
    String getState();
    String getDescription();
}
