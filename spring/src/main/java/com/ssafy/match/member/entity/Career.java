package com.ssafy.match.member.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import com.ssafy.match.member.entity.Member;
import lombok.*;

@Getter
@Setter
@Entity(name = "matching.career")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Career {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String company;
    @NotEmpty
    private String role;
//    @NotEmpty
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String description;

//    @NotEmpty
    private Boolean is_incumbent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Career(String company, String role, LocalDateTime start_date,
        LocalDateTime end_date, String description, Boolean is_incumbent,
        Member member) {
        this.company = company;
        this.role = role;
        this.start_date = start_date;
        this.end_date = end_date;
        this.description = description;
        this.is_incumbent = is_incumbent;
        this.member = member;
    }
}