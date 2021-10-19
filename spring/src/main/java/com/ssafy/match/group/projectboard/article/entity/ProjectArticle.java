package com.ssafy.match.group.projectboard.article.entity;

import com.ssafy.match.group.projectboard.article.dto.ProjectArticleRequestDto;
import com.ssafy.match.group.projectboard.board.entity.ProjectBoard;
import com.ssafy.match.member.entity.Member;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "matching.project_article")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProjectArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_board_id")
    private ProjectBoard projectBoard;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @NotEmpty
    private String title;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;
    @Column(name = "view_count")
    private int viewCount;

    public void update(ProjectArticleRequestDto dto, ProjectBoard projectBoard){
        this.projectBoard = projectBoard;
        this.title = dto.getTitle();
        this.modifiedDate = LocalDateTime.now();
    }

    public static ProjectArticle of(ProjectArticleRequestDto dto, ProjectBoard projectBoard, Member member){
        return ProjectArticle.builder()
            .projectBoard(projectBoard)
            .member(member)
            .title(dto.getTitle())
            .createDate(LocalDateTime.now())
            .modifiedDate(LocalDateTime.now())
            .viewCount(0)
            .build();
    }
}
