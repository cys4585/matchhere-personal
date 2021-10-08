package com.ssafy.match.group.entity.project;

import com.ssafy.match.db.entity.City;
import com.ssafy.match.file.entity.DBFile;
import com.ssafy.match.group.dto.project.request.ProjectApplicationRequestDto;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "matching.project_application_form")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectApplicationForm {

    @EmbeddedId
    private CompositeMemberProject compositeMemberProject;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private City city;

    private String role;

    private String position;

    private String git;

    private String twitter;

    private String facebook;

    private String backjoon;

    private String bio;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_pic")
    private DBFile dbFile;

    @Builder
    public ProjectApplicationForm(CompositeMemberProject cmp, ProjectApplicationRequestDto dto) {
        this.compositeMemberProject = cmp;
        this.nickname = dto.getNickname();
        this.position = dto.getPosition();
        this.city = City.from(dto.getCity());
        this.git = dto.getGit();
        this.twitter = dto.getTwitter();
        this.facebook = dto.getFacebook();
        this.backjoon = dto.getBackjoon();
        this.role = dto.getRole();
        this.bio = dto.getBio();
        this.createDate = LocalDateTime.now();
    }
}