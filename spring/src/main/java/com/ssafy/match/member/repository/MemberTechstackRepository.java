package com.ssafy.match.member.repository;

import com.ssafy.match.member.entity.MemberTechstack;
import com.ssafy.match.member.entity.composite.CompositeMemberTechstack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberTechstackRepository extends JpaRepository<MemberTechstack, CompositeMemberTechstack> {

}