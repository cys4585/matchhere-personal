package com.ssafy.match.member.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface EducationInterface {
    Long getId();
    String getInstitution();
    String getDegree();
    String getMajor();
    LocalDate getStart_date();
    LocalDate getEnd_date();
    String getState();
    String getDescription();
}
