package com.ssafy.match.group.entity.study;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "matching.study_techstack")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyTechstack {

    @EmbeddedId
    private CompositeStudyTechstack compositeStudyTechstack;

    public StudyTechstack(
        CompositeStudyTechstack compositeStudyTechstack) {
        this.compositeStudyTechstack = compositeStudyTechstack;
    }
}