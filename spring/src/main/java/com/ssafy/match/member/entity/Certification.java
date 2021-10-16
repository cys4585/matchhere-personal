package com.ssafy.match.member.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ssafy.match.member.entity.Member;
import lombok.*;


@Getter
@Setter
@Entity(name = "matching.certification")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String organization;
    private String code;
    private String grade;
    private LocalDateTime issued_date;
    private LocalDateTime expired_date;
    private Boolean is_expire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Certification(String name, String organization, String code, String grade,
        LocalDateTime issued_date, LocalDateTime expired_date, Boolean is_expire,
        Member member) {
        this.name = name;
        this.organization = organization;
        this.code = code;
        this.grade = grade;
        this.issued_date = issued_date;
        this.expired_date = expired_date;
        this.is_expire = is_expire;
        this.member = member;
    }
}