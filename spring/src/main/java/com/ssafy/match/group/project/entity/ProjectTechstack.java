package com.ssafy.match.group.project.entity;

import com.ssafy.match.common.entity.Level;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity(name = "matching.project_techstack")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProjectTechstack {

    @EmbeddedId
    private CompositeProjectTechstack compositeProjectTechstack;

    private Level level;

    public static ProjectTechstack of(CompositeProjectTechstack compositeProjectTechstack, Level level) {
        return ProjectTechstack.builder()
            .compositeProjectTechstack(compositeProjectTechstack)
            .level(level)
            .build();
    }
}