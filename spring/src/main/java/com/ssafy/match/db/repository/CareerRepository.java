package com.ssafy.match.db.repository;

import com.ssafy.match.db.entity.Career;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerRepository extends JpaRepository<Career, Integer> {

}