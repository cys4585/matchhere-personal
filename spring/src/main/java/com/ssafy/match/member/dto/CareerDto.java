package com.ssafy.match.member.dto;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class CareerDto {
    private String company;
    private String role;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private Boolean is_incumbent;
    private String description;
}
