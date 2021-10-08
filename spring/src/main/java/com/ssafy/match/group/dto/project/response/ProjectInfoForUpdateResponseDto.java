package com.ssafy.match.group.dto.project.response;

import com.ssafy.match.file.entity.DBFile;
import com.ssafy.match.group.dto.MemberDto;
import com.ssafy.match.group.dto.club.ClubDto;
import com.ssafy.match.group.entity.club.Club;
import com.ssafy.match.group.entity.project.Project;
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

@ApiModel(value = "프로젝트 수정  정보", description = "프로젝트 수정을 위한 정보 Response Dto Class")
@Getter
@Setter
public class ProjectInfoForUpdateResponseDto {

    @ApiModelProperty(name = "name", example = "알고리즘 프로젝트")
    @ApiParam(value = "프로젝트명", required = true)
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

    @ApiModelProperty(name = "developerCount", example = "3")
    @ApiParam(value = "개발자 현재 인원", required = true)
    private int developerCount;

    @ApiModelProperty(name = "developerMaxCount", example = "3")
    @ApiParam(value = "개발자 모집 인원", required = true)
    private int developerMaxCount;

    @ApiModelProperty(name = "designerCount", example = "3")
    @ApiParam(value = "디자이너 현재 인원", required = true)
    private int designerCount;

    @ApiModelProperty(name = "designerMaxCount", example = "3")
    @ApiParam(value = "디자이너 모집 인원", required = true)
    private int designerMaxCount;

    @ApiModelProperty(name = "plannerCount", example = "3")
    @ApiParam(value = "기획자 현재 인원", required = true)
    private int plannerCount;

    @ApiModelProperty(name = "plannerMaxCount", example = "3")
    @ApiParam(value = "기획자 모집 인원", required = true)
    private int plannerMaxCount;

    @ApiModelProperty(name = "isPublic", example = "false")
    @ApiParam(value = "공개 비공개", required = true)
    private Boolean isPublic;

    @ApiModelProperty(name = "isParticipate", example = "false")
    @ApiParam(value = "참여 가능 여부", required = true)
    private Boolean isParticipate;

    @ApiModelProperty(name = "city", example = "구미")
    @ApiParam(value = "지역", required = true)
    private String city;

    @ApiModelProperty(name = "status", example = "모집, 진행, 종료")
    @ApiParam(value = "프로젝트 상태", required = true)
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

    @ApiModelProperty(name = "bio", example = "Git 매칭 프로젝트입니다.")
    @ApiParam(value = "프로젝트 소개", required = true)
    private String bio;

    @ApiModelProperty(name = "projectTechstack", example = "[\"java\", \"python\"]")
    @ApiParam(value = "해당 프로젝트가 가지고 있는 기술 스택 리스트", required = true)
    private List<String> projectTechstack;

    @ApiModelProperty(name = "hostClub", example = "[{\"clubId\": 1, \"clubName\": \"첫 클럽\"}, {\"clubId\": 2, \"clubName\": \"두번째 클럽\"}]")
    @ApiParam(value = "해당 호스트가 포함되어있는 클럽 목록 (수정시 클럽 수정을 위한)", required = true)
    private List<ClubDto> clubList;

    @ApiModelProperty(name = "projectMember", example = "[{\"id\": 3, \"name\": \"문일민\", \"nickname\": \"별명\"}, {\"id\": 4, \"name\": \"박범진\", \"nickname\": \"내별명\"}]")
    @ApiParam(value = "해당 프로젝트에 속한 멤버 조회", required = true)
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
    public ProjectInfoForUpdateResponseDto(Project project) {
        this.name = project.getName();
        this.schedule = project.getSchedule();
        this.period = project.getPeriod();
        this.designerCount = project.getDesignerCount();
        this.designerMaxCount = project.getDesignerMaxCount();
        this.developerCount = project.getDeveloperCount();
        this.developerMaxCount = project.getDeveloperMaxCount();
        this.plannerCount = project.getPlannerCount();
        this.plannerMaxCount = project.getPlannerMaxCount();
        this.isPublic = project.getIsPublic();
        this.isParticipate = project.getIsParticipate();
        this.city = project.getCity().name();
        this.status = project.getStatus().name();
        setClub(project.getClub());
        setData(project.getDbFile());
        this.modifyDate = project.getModifyDate();
        this.bio = project.getBio();
    }
}
