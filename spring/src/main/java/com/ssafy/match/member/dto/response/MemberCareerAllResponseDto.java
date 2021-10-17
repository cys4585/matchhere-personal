package com.ssafy.match.member.dto.response;


import com.ssafy.match.member.dto.CareerInterface;
import com.ssafy.match.member.dto.CertificationInterface;
import com.ssafy.match.member.dto.EducationInterface;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MemberCareerAllResponseDto {
    private List<CareerInterface> careerList = new ArrayList<>();
    private List<EducationInterface> educationList = new ArrayList<>();
    private List<CertificationInterface> certificationList = new ArrayList<>();
}
