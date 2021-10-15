package com.ssafy.match.member.repository;

import com.ssafy.match.member.dto.CertificationDto;
import com.ssafy.match.member.entity.Certification;
import com.ssafy.match.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificationRepository extends JpaRepository<Certification, Long> {
    List<CertificationDto> findAllByMember(Member member);
}