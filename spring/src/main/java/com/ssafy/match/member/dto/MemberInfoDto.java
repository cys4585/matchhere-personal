package com.ssafy.match.member.dto;

import com.ssafy.match.file.entity.DBFile;
import com.ssafy.match.group.dto.club.response.ClubInfoResponseDto;
import com.ssafy.match.group.dto.project.response.ProjectInfoResponseDto;
import com.ssafy.match.group.dto.study.response.StudyInfoResponseDto;
import com.ssafy.match.group.entity.club.Club;
import com.ssafy.match.group.entity.project.Project;
import com.ssafy.match.group.entity.study.Study;
import com.ssafy.match.member.entity.Member;
import com.ssafy.match.member.entity.MemberSns;
import com.ssafy.match.member.entity.Position;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
public class MemberInfoDto {
    @ApiModelProperty(name = "email", example = "my_email@gmail.com")
    private String email;

    @ApiModelProperty(name = "name", example = "문일민")
    private String name;

    @ApiModelProperty(name = "nickname", example = "별명")
    private String nickname;

    @ApiModelProperty(name = "tel", example = "010-1234-4567")
    private String tel;

    @ApiModelProperty(name = "bio", example = "let me introduce")
    private String bio;

    @ApiModelProperty(name = "city", example = "부산")
    private String city;

    @ApiModelProperty(name = "position", example = "개발자")
    private String position;

    @ApiModelProperty(name = "cover_pic", example = "사진 uri")
    private String cover_pic;

    @ApiModelProperty(name = "portfolio", example = "포트폴리오 uri")
    private String portfolio;

    @ApiModelProperty(name = "portfolio_uri", example = "https://naver.com")
    private String portfolio_uri;

//    @ApiModelProperty(name = "myClubList", example = "[\"\",\"java\"]")
    private List<Club> myClubList = new ArrayList<>();
    @ApiModelProperty(name = "myProjectList", example = "[\"python\",\"java\"]")
    private List<Project> myProjectList = new ArrayList<>();
    private List<Study> myStudyList = new ArrayList<>();
    // 임시 수정요 일민님
//    private List<ClubInfoResponseDto> myClubList = new ArrayList<>();
//    @ApiModelProperty(name = "myProjectList", example = "[\"python\",\"java\"]")
//    private List<ProjectInfoResponseDto> myProjectList = new ArrayList<>();
//    private List<StudyInfoResponseDto> myStudyList = new ArrayList<>();
    ////////////////////////////////////
    @ApiModelProperty(name = "expTechList", example = "[\"python\",\"java\"]")
    private List<String> expTechList = new ArrayList<>();
    @ApiModelProperty(name = "beginTechList", example = "[\"python\",\"java\"]")
    private List<String> beginTechList = new ArrayList<>();
    @ApiModelProperty(name = "snsList", example = "[{\"id\":1, \"snsName\":\"github\", \"snsAccount\":\"gitid\"},{\"id\":2, \"snsName\":\"twitter\", \"snsAccount\":\"twitterid\"}]")
    private List<MemberSns> snsList = new ArrayList<>();
    @ApiModelProperty(name = "dpositionList", example = "[{\"id\":1, \"name\":\"frontend\"},{\"id\":2, \"name\":\"devops\"}]")
    private List<Position> dpositionList = new ArrayList<>();

//    public MemberInfoDto(Member member) {
//        email = member.getEmail();
//        name = member.getName();
//        nickname = member.getNickname();
//        tel = member.getTel();
//        bio = member.getBio();
//        city = member.getCity();
//        position = member.getPosition();
//        dbFile = member.getDbFile();
//    }
    public static MemberInfoDto of(Member member) {
        return MemberInfoDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .tel(member.getTel())
                .bio(member.getBio())
                .city(member.getCity())
                .position(member.getPosition())
//                .cover_pic(member.getCover_pic())
//                .portfolio(member.getPortfolio_uuid())
                .portfolio_uri(member.getPortfolio_uri())
                .build();
    }

    @Builder
    public MemberInfoDto(String email, String name, String nickname, String tel, String bio, String city, String position, String cover_pic, String portfolio, String portfolio_uri) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.tel = tel;
        this.bio = bio;
        this.city = city;
        this.position = position;
        this.cover_pic = cover_pic;
        this.portfolio = portfolio;
        this.portfolio_uri = portfolio_uri;
//        this.myClubList = myClubList;
    }

}