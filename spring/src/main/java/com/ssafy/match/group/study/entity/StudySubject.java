package com.ssafy.match.group.study.entity;

import com.ssafy.match.common.entity.Level;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "study_subject")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudySubject {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "study_id")
    private Study study;

    private String name;
    @Enumerated(EnumType.STRING)
    private Level level;

    @Builder
    public StudySubject(Study study, String name, Level level) {
        this.study = study;
        this.name = name;
        this.level = level;
    }
}