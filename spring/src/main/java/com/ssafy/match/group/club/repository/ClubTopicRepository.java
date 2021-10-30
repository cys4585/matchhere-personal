package com.ssafy.match.group.club.repository;

import com.ssafy.match.group.club.entity.ClubTopic;
import com.ssafy.match.group.club.entity.Club;
import com.ssafy.match.group.club.entity.ClubTopic;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ClubTopicRepository extends JpaRepository<ClubTopic, Long> {

    // 클럽의 기존 주제 전체 제거
    @Transactional
    @Modifying
    @Query("delete from ClubTopic ct where ct.club = :club")
    void deleteAllByClub(@Param("club") Club club);

    List<ClubTopic> findAllByClub(@Param("club") Club club);
}