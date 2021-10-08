package com.ssafy.match.group.dto.study.response;

import com.ssafy.match.file.entity.DBFile;
import com.ssafy.match.group.dto.MemberDto;
import com.ssafy.match.group.dto.club.ClubDto;
import com.ssafy.match.group.entity.club.Club;
import com.ssafy.match.group.entity.study.Study;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Lob;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "스터디 조회 정보", description = "스터디의 상세 정보 Response Dto Class")
@Getter
@Setter
public class StudyInfoResponseDto {

    @ApiModelProperty(name = "id", example = "4")
    private Long id;

    @ApiModelProperty(name = "name", example = "알고리즘 스터디")
    private String name;

    @ApiModelProperty(name = "schedule", example = "매주 화, 수 6시")
    private String schedule;

    @ApiModelProperty(name = "period", example = "7")
    private int period;

    @ApiModelProperty(name = "host", example = "[\"id\": 3, \"name\": \"문일민\", \"nickname\": \"장난꾸러기\"]")
    private MemberDto host;

    @ApiModelProperty(name = "memberCount", example = "3")
    private int memberCount;

    @ApiModelProperty(name = "maxCount", example = "3")
    private int maxCount;

    @ApiModelProperty(name = "isPublic", example = "false")
    private Boolean isPublic;

    @ApiModelProperty(name = "isParticipate", example = "false")
    private Boolean isParticipate;

    @ApiModelProperty(name = "city", example = "서울")
    private String city;

    @ApiModelProperty(name = "status", example = "모집, 진행, 종료 중 하나")
    @ApiParam(value = "스터디 상태", required = true)
    private String status;

    @ApiModelProperty(name = "club", example = "[\"id\": 3, \"name\": \"SSAFY\"]")
    private ClubDto club;

    @ApiModelProperty(name = "cover_pic", example = "커버사진 uri")
    private String cover_pic;

    @ApiModelProperty(name = "modifiedDate", example = "2021-09-06 06:57:37.667537")
    private LocalDateTime modifiedDate;

    @ApiModelProperty(name = "bio", example = "알고리즘 스터디입니다.")
    private String bio;

    @ApiModelProperty(name = "projectMember", example = "[{\"id\": 3, \"name\": \"문일민\", \"nickname\": \"별명\"}, {\"id\": 4, \"name\": \"박범진\", \"nickname\": \"내별명\"}]")
    private List<MemberDto> memberDtos;

    @ApiModelProperty(name = "techList", example = "[\"java\", \"python\"]")
    private List<String> techList;

    public void setClub(Club club){
        if(club == null) return;
        this.club = new ClubDto(club);
    }

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
                .host(new MemberDto(study.getMember()))
                .memberCount(study.getMemberCount())
                .maxCount(study.getMaxCount())
                .isPublic(study.getIsPublic())
                .isParticipate(study.getIsParticipate())
                .city(study.getCity().name())
                .status(study.getStatus().name())
                .club((study.getClub() == null) ? null : new ClubDto(study.getClub()))
                .cover_pic((study.getDbFile() == null) ? null : study.getDbFile().getDownload_uri())
                .modifiedDate(study.getModifyDate())
                .bio(study.getBio())
                .build();
    }

    @Builder
    public StudyInfoResponseDto(Long id, String name, String schedule, int period, MemberDto host, int memberCount, int maxCount, Boolean isPublic, Boolean isParticipate, String city, String status, ClubDto club, String cover_pic, LocalDateTime modifiedDate, String bio) {
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
