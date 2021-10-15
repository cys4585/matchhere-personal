package com.ssafy.match.member.dto.response;


import com.ssafy.match.member.dto.CareerDto;
import com.ssafy.match.member.dto.CertificationDto;
import com.ssafy.match.member.dto.EducationDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MemberCareerResponseDto {
    private List<CareerDto> careerList = new ArrayList<>();
    private List<EducationDto> educationList = new ArrayList<>();
    private List<CertificationDto> certificationList = new ArrayList<>();
}
