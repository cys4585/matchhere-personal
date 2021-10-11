package com.ssafy.match.member.repository;


import com.ssafy.match.member.entity.EmailCheck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface EmailCheckRepository extends JpaRepository<EmailCheck, Long> {
    Optional<EmailCheck> findByEmail(String email);
}
