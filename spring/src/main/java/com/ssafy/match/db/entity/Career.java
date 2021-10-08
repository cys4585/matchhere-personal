package com.ssafy.match.db.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ssafy.match.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "matching.career")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Career {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String company;
    private String department;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Career(String company, String department, LocalDateTime start_date,
        LocalDateTime end_date, String description, Member member) {
        this.company = company;
        this.department = department;
        this.start_date = start_date;
        this.end_date = end_date;
        this.description = description;
        this.member = member;
    }
}