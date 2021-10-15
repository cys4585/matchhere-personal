package com.ssafy.match.member.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CertificationDto {
    private String name;
    private String organization;
    private String code;
    private String grade;
    private LocalDateTime issued_date;
    private LocalDateTime expired_date;
    private Boolean is_expire;
}
