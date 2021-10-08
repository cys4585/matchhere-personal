package com.ssafy.match.member.repository;

import com.ssafy.match.member.entity.Member;
import com.ssafy.match.member.entity.MemberExperiencedTechstack;
import com.ssafy.match.db.entity.Techstack;
import com.ssafy.match.db.entity.embedded.CompositeMemberTechstack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.query.Param;

// https://whitekeyboard.tistory.com/313
public interface MemberExperiencedTechstackRepository extends
    JpaRepository<MemberExperiencedTechstack, CompositeMemberTechstack> {
//    boolean existsByCompositeMemberTechstack_MemberAndCompositeMemberTechstack_NameTechstack(Member member, String techstack);
    Optional<MemberExperiencedTechstack> findByCompositeMemberTechstack_MemberAndCompositeMemberTechstack_Techstack(Member member, Techstack techstack);

    @Query(value = "select mt.compositeMemberTechstack.techstack.name from matching.member_experienced_techstack mt where mt.compositeMemberTechstack.member = :member")
    List<String> findTechstackByMemberName(@Param("member") Member member);

}