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

@ApiModel(value = "스터디 수정 정보", description = "스터디의  수정을 위한 정보 Response Dto Class")
@Getter
@Setter
public class StudyInfoForUpdateResponseDto {

    @ApiModelProperty(name = "name", example = "알고리즘 스터디")
    @ApiParam(value = "스터디명", required = true)
    private String name;

    @ApiModelProperty(name = "schedule", example = "매주 화, 수 6시")
    @ApiParam(value = "작업 시간", required = true)
    private String schedule;

    @ApiModelProperty(name = "period", example = "7")
    @ApiParam(value = "기간(주 단위)", required = true)
    private int period;

    @ApiModelProperty(name = "host", example = "{\"id\": 3, \"name\": \"박범진\", \"nickname\": \"BJP\"}")
    @ApiParam(value = "스터디장 정보(id, name, nickname)", required = true)
    private MemberDto host;

    @ApiModelProperty(name = "memberCount", example = "3")
    @ApiParam(value = "현재 인원", required = true)
    private int memberCount;

    @ApiModelProperty(name = "maxCount", example = "3")
    @ApiParam(value = "최대 인원", required = true)
    private int maxCount;

    @ApiModelProperty(name = "isPublic", example = "false")
    @ApiParam(value = "공개 비공개", required = true)
    private Boolean isPublic;

    @ApiModelProperty(name = "isParticipate", example = "false")
    @ApiParam(value = "참여 가능 여부", required = true)
    private Boolean isParticipate;

    @ApiModelProperty(name = "city", example = "구미")
    @ApiParam(value = "지역", required = true)
    private String city;

    @ApiModelProperty(name = "status", example = "모집중, 진행중, 종료됨")
    @ApiParam(value = "스터디 상태", required = true)
    private String status;

    @ApiModelProperty(name = "clubId", example = "{\"id\": 3, \"name\": \"SSAFY\"}")
    @ApiParam(value = "소속된 클럽 정보")
    private ClubDto club;

    @ApiModelProperty(name = "data", example = "사진을 보이기 위한 바이트")
    @Lob // DBFile 객체 반환시 InvalidDefinitionException: No serializer found for class
    private byte[] data;

    @ApiModelProperty(name = "modifyDate", example = "2021-09-06 06:57:37.667537")
    @ApiParam(value = "마지막 수정일")
    private LocalDateTime modifyDate;

    @ApiModelProperty(name = "bio", example = "매칭 스터디입니다.")
    @ApiParam(value = "스터디 소개", required = true)
    private String bio;

    @ApiModelProperty(name = "studyTechstack", example = "[\"java\", \"python\"]")
    @ApiParam(value = "해당 스터디가 가지고 있는 기술 스택 리스트", required = true)
    private List<String> studyTechstack;

    @ApiModelProperty(name = "hostClub", example = "[{\"clubId\": 1, \"clubName\": \"첫 클럽\"}, {\"clubId\": 2, \"clubName\": \"두번째 클럽\"}]")
    @ApiParam(value = "해당 호스트가 포함되어있는 클럽 목록 (수정시 클럽 수정을 위한)", required = true)
    private List<ClubDto> clubList;

    @ApiModelProperty(name = "memberDtos", example = "[{\"id\": 3, \"name\": \"문일민\", \"nickname\": \"별명\"}, {\"id\": 4, \"name\": \"박범진\", \"nickname\": \"내별명\"}]")
    @ApiParam(value = "해당 스터디에 속한 멤버 조회", required = true)
    private List<MemberDto> memberDtos;

    public void setClub(Club club){
        if(club == null) return;
        this.club = new ClubDto(club);
    }

    public void setData(DBFile dbFile){
        if(dbFile == null) return;
        this.data = dbFile.getData();
    }

    @Builder
    public StudyInfoForUpdateResponseDto(Study study) {
        this.name = study.getName();
        this.schedule = study.getSchedule();
        this.period = study.getPeriod();
        this.maxCount = study.getMaxCount();
        this.isPublic = study.getIsPublic();
        this.isParticipate = study.getIsParticipate();
        this.city = study.getCity().name();
        this.status = study.getStatus().name();
        setClub(study.getClub());
        setData(study.getDbFile());
        this.modifyDate = study.getModifyDate();
        this.bio = study.getBio();
    }
}
