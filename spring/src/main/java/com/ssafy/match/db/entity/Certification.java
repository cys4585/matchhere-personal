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
@Entity(name = "matching.certification")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String organization;
    private String code;
    private String grade;
    private LocalDateTime issued_date;
    private LocalDateTime expired_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Certification(String name, String organization, String code, String grade,
        LocalDateTime issued_date, LocalDateTime expired_date, Member member) {
        this.name = name;
        this.organization = organization;
        this.code = code;
        this.grade = grade;
        this.issued_date = issued_date;
        this.expired_date = expired_date;
        this.member = member;
    }
}