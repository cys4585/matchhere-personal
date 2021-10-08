package com.ssafy.match.member.repository;

import com.ssafy.match.member.entity.Member;
import com.ssafy.match.member.entity.MemberBeginnerTechstack;
import com.ssafy.match.db.entity.Techstack;
import com.ssafy.match.db.entity.embedded.CompositeMemberTechstack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.query.Param;

public interface MemberBeginnerTechstackRepository extends
        JpaRepository<MemberBeginnerTechstack, CompositeMemberTechstack> {
    Optional<MemberBeginnerTechstack> findByCompositeMemberTechstack_MemberAndCompositeMemberTechstack_Techstack(Member member, Techstack techstack);

    @Query(value = "select mt.compositeMemberTechstack.techstack.name from matching.member_beginner_techstack mt where mt.compositeMemberTechstack.member = :member")
    List<String> findTechstackByMemberName(@Param("member") Member member);
}
