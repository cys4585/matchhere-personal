package com.ssafy.match.member.dto;


import java.time.LocalDateTime;

public interface CertificationInterface {
    String getName();
    String getOrganization();
    String getCode();
    String getGrade();
    LocalDateTime getIssued_date();
    LocalDateTime getExpired_date();
    Boolean getIs_expire();
}
