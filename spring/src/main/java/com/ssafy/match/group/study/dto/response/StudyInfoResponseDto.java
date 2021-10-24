package com.ssafy.match.group.study.dto.response;

import com.ssafy.match.file.entity.DBFile;
import com.ssafy.match.group.project.dto.response.ProjectTechstackResponseDto;
import com.ssafy.match.member.dto.MemberSimpleInfoResponseDto;
import com.ssafy.match.group.club.dto.response.ClubSimpleInfoResponseDto;
import com.ssafy.match.group.club.entity.Club;
import com.ssafy.match.group.study.entity.Study;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "스터디 조회 정보", description = "스터디의 상세 정보 Response Dto Class")
@Getter
@Builder
@AllArgsConstructor
public class StudyInfoResponseDto {

    @ApiModelProperty(example = "4")
    private Long id;

    @ApiModelProperty(example = "http://localhost:8080/api/downloadFile/97534f05-7e7f-425d-ac3e-aae8acee8a42")
    @ApiParam(value = "파일 다운로드 Uri")
    private String coverPicUri;

    @ApiModelProperty(example = "알고리즘 스터디")
    private String name;

    @ApiModelProperty(example = "스터디 진행 중")
    @ApiParam(value = "스터디 진행 상태")
    private String studyProgressState;

    @ApiModelProperty(example = "모집 중")
    @ApiParam(value = "모집 상태")
    private String recruitmentState;

    @ApiModelProperty(example = "32321")
    @ApiParam(value = "조회수")
    private int viewCount;

    @ApiModelProperty(example = "2021-09-06 06:57:37.667537")
    @ApiParam(value = "생성일자")
    private LocalDateTime createDate;

    @ApiModelProperty(example = "[\"id\": 3, \"name\": \"박범진\", \"nickname\": \"BJP\"]")
    @ApiParam(value = "스터디장 정보(id, name, nickname)")
    private MemberSimpleInfoResponseDto host;

    @ApiModelProperty(example = "[{\"name\":\"python\", \"level\":\"상\"}, {\"name\":\"spring\", \"level\":\"하\"]")
    @ApiParam(value = "스터디 주제 리스트")
    private List<StudyTopicResponseDto> topics;

    @ApiModelProperty(example = "매주 화, 수 6시")
    private String schedule;

    @ApiModelProperty(example = "2020-05-22")
    @ApiParam(value = "프로젝트 마감 예정일")
    private LocalDate period;

    @ApiModelProperty(example = "3")
    @ApiParam(value = "스터디 현재 인원")
    private int memberCount;

    @ApiModelProperty(name = "maxCount", example = "3")
    private int maxCount;

    @ApiModelProperty(name = "city", example = "서울")
    private String city;

    @ApiModelProperty(name = "club", example = "[\"id\": 3, \"name\": \"SSAFY\"]")
    private ClubSimpleInfoResponseDto club;

    @ApiModelProperty(name = "modifiedDate", example = "2021-09-06 06:57:37.667537")
    private LocalDateTime modifiedDate;

    @ApiModelProperty(name = "bio", example = "알고리즘 스터디입니다.")
    private String bio;

    @ApiModelProperty(name = "projectMember", example = "[{\"id\": 3, \"name\": \"문일민\", \"nickname\": \"별명\"}, {\"id\": 4, \"name\": \"박범진\", \"nickname\": \"내별명\"}]")
    private List<MemberSimpleInfoResponseDto> memberSimpleInfoResponseDtos;

    @ApiModelProperty(name = "techList", example = "[\"java\", \"python\"]")
    private List<String> techList;

//    public void setClub(Club club){
//        if(club == null) return;
//        this.club = new ClubSimpleInfoResponseDto(club);
//    }

    public void setData(DBFile dbFile){
        if(dbFile == null) return;
        this.cover_pic = dbFile.getDownload_uri();
    }

    public static StudyInfoResponseDto of(Study study) {
        return StudyInfoResponseDto.builder()
                .id(study.getId())
                .name(study.getName())
                .schedule(study.getSchedule())
                .period(study.getPeriod())
//                .host(new MemberSimpleInfoResponseDto(study.getMember()))
                .memberCount(study.getMemberCount())
                .maxCount(study.getMaxCount())
                .isPublic(study.getIsPublic())
                .isParticipate(study.getIsParticipate())
                .city(study.getCity().name())
                .status(study.getStudyProgressState().name())
//                .club((study.getClub() == null) ? null : new ClubSimpleInfoResponseDto(study.getClub()))
//                .cover_pic((study.getDbFile() == null) ? null : study.getDbFile().getDownload_uri())
                .modifiedDate(study.getModifyDate())
                .bio(study.getBio())
                .build();
    }

    @Builder
    public StudyInfoResponseDto(Long id, String name, String schedule, int period, MemberSimpleInfoResponseDto host, int memberCount, int maxCount, Boolean isPublic, Boolean isParticipate, String city, String status, ClubSimpleInfoResponseDto club, String cover_pic, LocalDateTime modifiedDate, String bio) {
        this.id = id;
        this.name = name;
        this.schedule = schedule;
        this.period = period;
        this.host = host;
        this.memberCount = memberCount;
        this.maxCount = maxCount;
        this.isPublic = isPublic;
        this.isParticipate = isParticipate;
        this.city = city;
        this.status = status;
        this.club = club;
        this.cover_pic = cover_pic;
        this.modifiedDate = modifiedDate;
        this.bio = bio;
    }
}
