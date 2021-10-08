package com.ssafy.match.group.studyboard.comment.entity;

import com.ssafy.match.group.studyboard.article.entity.StudyArticle;
import com.ssafy.match.group.studyboard.comment.dto.StudyArticleCommentRequestDto;
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
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "study_article_comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyArticleComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;
    private int depth;

    @Column(name = "parent_id")
    private Long parentId;

//    @Column(name = "group_id") 뎁스가 1이 아니고 더 깊어진다면 필요
//    private Long groupId;

    @Column(name = "reply_count")
    private int replyCount;

    @Column(name = "is_modified")
    private Boolean isModified;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_article_id")
    private StudyArticle studyArticle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void addReplyCount(){
        replyCount++;
    }

    public void setDepth(Long parentId){
        if(parentId > 0){
            this.depth = 1;
        }else{
            this.depth = 0;
        }
        this.parentId = parentId;
    }

    @Builder
    public StudyArticleComment(Long parentId, StudyArticleCommentRequestDto studyArticleCommentRequestDto, Member member, StudyArticle studyArticle) {
        this.member = member;
        this.studyArticle = studyArticle;
        this.content = studyArticleCommentRequestDto.getContent();
        this.createDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
        this.isModified = false;
        this.isDeleted = false;
        setDepth(parentId);
        this.replyCount = 0;
    }
}
