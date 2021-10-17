package com.ssafy.match.member.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;


public interface CareerInterface {
    Long getId();
    String getCompany();
    String getRole();
    LocalDate getStart_date();
    LocalDate getEnd_date();
    Boolean getIs_incumbent();
    String getDescription();
}
