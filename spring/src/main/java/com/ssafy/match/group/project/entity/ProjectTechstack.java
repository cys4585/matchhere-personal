package com.ssafy.match.group.project.entity;

import com.ssafy.match.common.entity.Level;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "matching.project_techstack")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectTechstack {

    @EmbeddedId
    private CompositeProjectTechstack compositeProjectTechstack;

    private Level level;

    @Builder
    public ProjectTechstack(
        CompositeProjectTechstack compositeProjectTechstack, Level level) {
        this.compositeProjectTechstack = compositeProjectTechstack;
        this.level = level;
    }
}