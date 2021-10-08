package com.ssafy.match.member.repository;

import com.ssafy.match.member.entity.Member;
import com.ssafy.match.member.entity.MemberSns;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberSnsRepository extends JpaRepository<MemberSns, Integer> {
    Optional<MemberSns> findByMemberAndSnsName(Member member, String snsName);
    List<MemberSns> findAllByMember(Member member);
}