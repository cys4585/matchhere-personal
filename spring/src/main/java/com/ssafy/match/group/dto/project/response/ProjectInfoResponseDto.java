package com.ssafy.match.group.dto.project.response;

import com.ssafy.match.file.entity.DBFile;
import com.ssafy.match.group.dto.MemberDto;
import com.ssafy.match.group.dto.club.ClubDto;
import com.ssafy.match.group.entity.club.Club;
import com.ssafy.match.group.entity.project.Project;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Lob;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectInfoResponseDto {

    @ApiModelProperty(name = "id", example = "4")
    private Long id;

    @ApiModelProperty(name = "name", example = "매칭 프로젝트")
    @ApiParam(value = "프로젝트명", required = true)
    private String name;

    @ApiModelProperty(name = "schedule", example = "매주 화, 수 6시")
    @ApiParam(value = "프로젝트 작업 시간", required = true)
    private String schedule;

    @ApiModelProperty(name = "period", example = "7")
    @ApiParam(value = "프로젝트 기간(주 단위)", required = true)
    private int period;

    @ApiModelProperty(name = "host", example = "[\"id\": 3, \"name\": \"박범진\", \"nickname\": \"BJP\"]")
    @ApiParam(value = "프로젝트장 정보(id, name, nickname)", required = true)
    private MemberDto host;

    @ApiModelProperty(name = "developerCount", example = "3")
    @ApiParam(value = "개발자 현재 인원", required = true)
    private int developerCount;

    @ApiModelProperty(name = "developerNicknames", example = "[\"BJP\", \"닉넴\"]")
    @ApiParam(value = "해당 프로젝트에 속해있는 개발자의 닉네임", required = true)
    private List<String> developerNicknames;

    @ApiModelProperty(name = "developerMaxCount", example = "3")
    @ApiParam(value = "개발자 모집 인원", required = true)
    private int developerMaxCount;

    @ApiModelProperty(name = "designerCount", example = "3")
    @ApiParam(value = "디자이너 현재 인원", required = true)
    private int designerCount;

    @ApiModelProperty(name = "designerNicknames", example = "[\"BJP\", \"닉넴\"]")
    @ApiParam(value = "해당 프로젝트에 속해있는 디자이너의 닉네임", required = true)
    private List<String> designerNicknames;

    @ApiModelProperty(name = "designerMaxCount", example = "3")
    @ApiParam(value = "디자이너 모집 인원", required = true)
    private int designerMaxCount;

    @ApiModelProperty(name = "plannerCount", example = "3")
    @ApiParam(value = "기획자 현재 인원", required = true)
    private int plannerCount;

    @ApiModelProperty(name = "plannerNicknames", example = "[\"BJP\", \"닉넴\"]")
    @ApiParam(value = "해당 프로젝트에 속해있는 기획자의 닉네임", required = true)
    private List<String> plannerNicknames;

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
    @ApiParam(value = "활동지역", required = true)
    private String city;

    @ApiModelProperty(name = "status", example = "모집, 진행, 종료 중 하나")
    @ApiParam(value = "프로젝트 상태", required = true)
    private String status;

    @ApiModelProperty(name = "clubId", example = "[\"id\": 3, \"name\": \"SSAFY\"]")
    @ApiParam(value = "소속된 클럽 정보")
    private ClubDto club;

    @ApiModelProperty(name = "cover_pic", example = "사진 uri")
//    @Lob // DBFile 객체 반환시 InvalidDefinitionException: No serializer found for class
    private String cover_pic;

    @ApiModelProperty(name = "modifyDate", example = "2021-09-06 06:57:37.667537")
    @ApiParam(value = "마지막 수정일")
    private LocalDateTime modifiedDate;

    @ApiModelProperty(name = "bio", example = "Git 매칭 프로젝트입니다.")
    @ApiParam(value = "프로젝트 소개", required = true)
    private String bio;

    @ApiModelProperty(name = "projectMember", example = "[{\"id\": 4, \"name\": \"문일민\", \"nickname\": \"별명\"}, {\"id\": 4, \"name\": \"박범진\", \"nickname\": \"내별명\"}]")
    @ApiParam(value = "해당 스터디에 속한 멤버 조회", required = true)
    private List<MemberDto> memberDtos;

    @ApiModelProperty(name = "techList", example = "[\"java\", \"python\"]")
    @ApiParam(value = "프로젝트 기술 스택", required = true)
    private List<String> techList;

    public static ProjectInfoResponseDto of(Project project) {
        return ProjectInfoResponseDto.builder()
                .id(project.getId())
                .name(project.getName())
                .schedule(project.getSchedule())
                .period(project.getPeriod())
                .host(new MemberDto(project.getMember()))
                .developerCount(project.getDeveloperCount())
                .developerMaxCount(project.getDeveloperMaxCount())
                .designerCount(project.getDesignerCount())
                .designerMaxCount(project.getDesignerMaxCount())
                .plannerCount(project.getPlannerCount())
                .plannerMaxCount(project.getPlannerMaxCount())
                .isPublic(project.getIsPublic())
                .isParticipate(project.getIsParticipate())
                .city(project.getCity().name())
                .status(project.getStatus().name())
                .club((project.getClub() == null) ? null : new ClubDto(project.getClub()))
                .cover_pic((project.getDbFile() == null) ? null : project.getDbFile().getDownload_uri())
                .modifiedDate(project.getModifyDate())
                .bio(project.getBio())
                .build();
    }

    @Builder
    public ProjectInfoResponseDto(Long id, String name, String schedule, int period, MemberDto host, int developerCount, int developerMaxCount, int designerCount, int designerMaxCount, int plannerCount, int plannerMaxCount, Boolean isPublic, Boolean isParticipate, String city, String status, ClubDto club, String cover_pic, LocalDateTime modifiedDate, String bio) {
        this.id = id;
        this.name = name;
        this.schedule = schedule;
        this.period = period;
        this.host = host;
        this.developerCount = developerCount;
        this.developerMaxCount = developerMaxCount;
        this.designerCount = designerCount;
        this.designerMaxCount = designerMaxCount;
        this.plannerCount = plannerCount;
        this.plannerMaxCount = plannerMaxCount;
        this.isPublic = isPublic;
        this.isParticipate = isParticipate;
        this.city = city;
        this.status = status;
        this.club = club;
        this.cover_pic = cover_pic;
        this.modifiedDate = modifiedDate;
        this.bio = bio;
    }

//    public void setClub(Club club){
//        if(club == null) return;
//        this.club = new ClubDto(club);
//    }
//
//    public void setData(DBFile dbFile){
//        if(dbFile == null) return;
//        this.data = dbFile.getData();
//    }
//
//    @Builder
//    public ProjectInfoResponseDto(Project project) {
//        this.id = project.getId();
//        this.developerCount = project.getDeveloperCount();
//        this.developerMaxCount = project.getDeveloperMaxCount();
//        this.designerCount = project.getDesignerCount();
//        this.designerMaxCount = project.getDesignerMaxCount();
//        this.plannerCount = project.getPlannerCount();
//        this.plannerMaxCount = project.getPlannerMaxCount();
//        this.name = project.getName();
//        this.schedule = project.getSchedule();
//        this.period = project.getPeriod();
//        this.isPublic = project.getIsPublic();
//        this.isParticipate = project.getIsParticipate();
//        this.city = project.getCity().name();
//        this.status = project.getStatus().name();
//        setClub(project.getClub());
//        setData(project.getDbFile());
//        this.modifyDate = project.getModifyDate();
//        this.bio = project.getBio();
//    }
}
