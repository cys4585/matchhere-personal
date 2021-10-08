package com.ssafy.match.db.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ssafy.match.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "matching.article")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String content;
    private LocalDateTime create_date;
    private LocalDateTime modify_date;
    private boolean is_deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Article(String title, String content, LocalDateTime create_date,
        LocalDateTime modify_date, boolean is_deleted, Board board, Member member) {
        this.title = title;
        this.content = content;
        this.create_date = create_date;
        this.modify_date = modify_date;
        this.is_deleted = is_deleted;
        this.board = board;
        this.member = member;
    }
}