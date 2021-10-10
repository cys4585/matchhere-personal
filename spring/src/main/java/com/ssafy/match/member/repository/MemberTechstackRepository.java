package com.ssafy.match.member.repository;

import com.ssafy.match.member.entity.Member;
import com.ssafy.match.member.entity.MemberTechstack;
import com.ssafy.match.member.entity.composite.CompositeMemberTechstack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberTechstackRepository extends JpaRepository<MemberTechstack, CompositeMemberTechstack> {

    @Query(value = "select mt.compositeMemberTechstack.techstack.name from matching.member_techstack mt where mt.compositeMemberTechstack.member = :member")
    List<String> findTechstackByMemberName(@Param("member") Member member);

}