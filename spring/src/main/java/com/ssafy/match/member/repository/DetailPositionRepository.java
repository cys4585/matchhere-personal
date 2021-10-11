package com.ssafy.match.member.repository;

import com.ssafy.match.member.entity.Member;
import com.ssafy.match.member.entity.DetailPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DetailPositionRepository extends JpaRepository<DetailPosition, Integer> {
    List<DetailPosition> findAllByMember(Member member);

    boolean existsByMemberAndName(Member member, String name);

    Optional<DetailPosition> findByMemberAndName(Member member, String name);
}