package com.ssafy.match.member.repository;

import com.ssafy.match.member.entity.MemberTechstack;
import com.ssafy.match.db.entity.embedded.CompositeMemberTechstack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberTechstackRepository extends JpaRepository<MemberTechstack, CompositeMemberTechstack> {

}