package com.ssafy.match.member.repository;

import com.ssafy.match.member.dto.EducationDto;
import com.ssafy.match.member.entity.Education;
import com.ssafy.match.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long> {
    List<EducationDto> findAllByMember(Member member);
}