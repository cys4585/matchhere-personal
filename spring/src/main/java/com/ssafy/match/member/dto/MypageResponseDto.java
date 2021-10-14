package com.ssafy.match.member.dto;

import com.ssafy.match.common.dto.DetailPositionInterface;
import com.ssafy.match.group.club.dto.response.ClubInfoResponseDto;
import com.ssafy.match.group.project.dto.response.ProjectInfoResponseDto;
import com.ssafy.match.group.study.dto.response.StudyInfoResponseDto;
import com.ssafy.match.member.entity.Member;
import com.ssafy.match.member.entity.MemberSns;
import com.ssafy.match.common.entity.DetailPosition;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
public class MypageResponseDto {
    @ApiModelProperty(name = "email", example = "my_email@gmail.com")
    private String email;

    @ApiModelProperty(name = "name", example = "문일민")
    private String name;

    @ApiModelProperty(name = "nickname", example = "별명")
    private String nickname;

//    @ApiModelProperty(name = "tel", example = "010-1234-4567")
//    private String tel;

    @ApiModelProperty(name = "bio", example = "let me introduce")
    private String bio;

    @ApiModelProperty(name = "city", example = "부산")
    private String city;

    @ApiModelProperty(name = "position", example = "개발자")
    private String position;

    @ApiModelProperty(name = "cover_pic", example = "http://cdn.matchhere.me/path/coverpic.png")
    private String cover_pic;

    @ApiModelProperty(name = "portfolio", example = "http://cdn.matchhere.me/path/portfolio.pdf")
    private String portfolio;

    @ApiModelProperty(name = "portfolio_uri", example = "https://naver.com")
    private String portfolio_uri;

    private List<ClubInfoResponseDto> myClubList = new ArrayList<>();
    private List<ProjectInfoResponseDto> myProjectList = new ArrayList<>();
    private List<StudyInfoResponseDto> myStudyList = new ArrayList<>();

//    @ApiModelProperty(name = "techList", example = "[{\"python\":{\"level\":\"상\", \"img_uri\":\"http://cdn.matchhere.me/path/python.png\"}}, {\"java\":{\"level\":\"중\", \"img_uri\":\"http://cdn.matchhere.me/path/java.png\"}}]")
    @ApiModelProperty(name = "techList", example = "[{\"name\":\"python\", \"level\":\"상\", \"img_uri\":\"http://cdn.matchhere.me/path/python.png\"}, {\"name\":\"java\", \"level\":\"중\", \"img_uri\":\"http://cdn.matchhere.me/path/java.png\"}]")
    private List<MemberTechstackInterface> techList = new ArrayList<>();
    @ApiModelProperty(name = "snsList", example = "[{\"id\":1, \"snsName\":\"github\", \"snsAccount\":\"gitid\"},{\"id\":2, \"snsName\":\"twitter\", \"snsAccount\":\"twitterid\"}]")
    private List<MemberSns> snsList = new ArrayList<>();
    @ApiModelProperty(name = "dpositionList", example = "[{\"id\":1, \"name\":\"frontend\"},{\"id\":2, \"name\":\"devops\"}]")
    private List<DetailPositionInterface> dpositionList = new ArrayList<>();

    public static MypageResponseDto of(Member member) {
        return MypageResponseDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
//                .tel(member.getTel())
                .bio(member.getBio())
                .city(member.getCity())
                .position(member.getPosition())
                .portfolio_uri(member.getPortfolio_uri())
                .build();
    }

    @Builder
    public MypageResponseDto(String email, String name, String nickname, String bio, String city, String position, String cover_pic, String portfolio, String portfolio_uri) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
//        this.tel = tel;
        this.bio = bio;
        this.city = city;
        this.position = position;
        this.cover_pic = cover_pic;
        this.portfolio = portfolio;
        this.portfolio_uri = portfolio_uri;
    }

}