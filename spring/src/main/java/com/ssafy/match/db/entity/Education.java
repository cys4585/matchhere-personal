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
@Entity(name = "matching.education")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Education {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String institution;
    private String degree;
    private String major;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private float my_credit;
    private float full_credit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public String getInstitution() {
        return institution;
    }

    public String getDegree() {
        return degree;
    }

    public String getMajor() {
        return major;
    }

    public LocalDateTime getStart_date() {
        return start_date;
    }

    public LocalDateTime getEnd_date() {
        return end_date;
    }

    public float getMy_credit() {
        return my_credit;
    }

    public float getFull_credit() {
        return full_credit;
    }

    public Member getMember() {
        return member;
    }
}