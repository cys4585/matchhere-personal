package com.ssafy.match.common.repository;

import com.ssafy.match.common.dto.DetailPositionInterface;
import com.ssafy.match.member.entity.Member;
import com.ssafy.match.common.entity.DetailPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DetailPositionRepository extends JpaRepository<DetailPosition, Integer> {
    List<DetailPositionInterface> findAllByMember(Member member);

    boolean existsByMemberAndName(Member member, String name);

    Optional<DetailPosition> findByMemberAndName(Member member, String name);
}