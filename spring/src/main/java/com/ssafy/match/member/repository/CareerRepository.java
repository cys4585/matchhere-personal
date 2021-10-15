package com.ssafy.match.member.repository;

import com.ssafy.match.member.dto.CareerInterface;
import com.ssafy.match.member.entity.Career;
import com.ssafy.match.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CareerRepository extends JpaRepository<Career, Long> {
    @Query(value = "select mc.company as company, mc.role as role, mc.start_date as start_date, mc.end_date as end_date, mc.is_incumbent as is_incumbent, mc.description as description  from matching.career mc where mc.member = :member")
    List<CareerInterface> findAllByMember(@Param("member") Member member);
}