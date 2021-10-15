package com.ssafy.match.member.entity;

import com.ssafy.match.common.entity.State;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
    private Long id;

    private String institution;
    private String degree;
    private String major;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private State state;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Education(String institution, String degree, String major,
        LocalDateTime start_date, LocalDateTime end_date, State state, String description,
        Member member) {
        this.institution = institution;
        this.degree = degree;
        this.major = major;
        this.start_date = start_date;
        this.end_date = end_date;
        this.state = state;
        this.description = description;
        this.member = member;
    }
}