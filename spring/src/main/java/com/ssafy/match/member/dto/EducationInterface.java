package com.ssafy.match.member.dto;


import com.ssafy.match.common.entity.State;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EducationInterface {
    private String institution;
    private String degree;
    private String major;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private State state;
    private String description;
}
