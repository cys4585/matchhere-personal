package com.ssafy.match.member.repository;

import com.ssafy.match.member.dto.CareerDto;
import com.ssafy.match.member.entity.Career;
import com.ssafy.match.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CareerRepository extends JpaRepository<Career, Long> {
    List<CareerDto> findAllByMember(Member member);
}