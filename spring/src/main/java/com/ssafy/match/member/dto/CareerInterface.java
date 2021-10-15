package com.ssafy.match.member.dto;

import lombok.Data;

import java.time.LocalDateTime;


public interface CareerInterface {
    String getCompany();
    String getRole();
    LocalDateTime getStart_date();
    LocalDateTime getEnd_date();
    Boolean getIs_incumbent();
    String getDescription();
}
