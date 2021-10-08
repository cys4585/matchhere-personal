package com.ssafy.match.group.projectboard.article.entity;

import com.ssafy.match.group.projectboard.board.entity.ProjectBoard;
import com.ssafy.match.member.entity.Member;
import java.time.LocalDateTime;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "matching.project_article")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @Column(name = "title")
    private String title;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(name = "view_count")
    private Integer viewCount;

    @Builder
    public ProjectArticle(ProjectBoard projectBoard, Member member, String title, LocalDateTime createDate, LocalDateTime modifiedDate, Integer viewCount) {
        this.projectBoard = projectBoard;
        this.member = member;
        this.title = title;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
        this.viewCount = viewCount;
    }
}
