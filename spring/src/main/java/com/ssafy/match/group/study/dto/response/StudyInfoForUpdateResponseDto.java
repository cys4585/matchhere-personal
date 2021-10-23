package com.ssafy.match.group.study.dto.response;

import com.ssafy.match.group.club.dto.response.ClubSimpleInfoResponseDto;
import com.ssafy.match.group.study.entity.Study;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@ApiModel(value = "스터디 수정 정보", description = "스터디의  수정을 위한 정보 Response Dto Class")
@Getter
@Builder
@AllArgsConstructor
public class StudyInfoForUpdateResponseDto {

    @ApiModelProperty(example = "3")
    @ApiParam(value = "스터디 Id")
    private Long id;

    @ApiModelProperty(example = "알고리즘 스터디")
    @ApiParam(value = "스터디명")
    private String name;

    @ApiModelProperty(example = "스터디 진행 중")
    @ApiParam(value = "스터디 진행 상태")
    private String studyProgressState;

    @ApiModelProperty(example = "http://localhost:8080/api/downloadFile/97534f05-7e7f-425d-ac3e-aae8acee8a42")
    @ApiParam(value = "파일 다운로드 Uri")
    private String coverPicUri;

    @ApiModelProperty(example = "{\"OS\":\"중\", \"데이터베이스\":\"상\"}")
    @ApiParam(value = "주제, 난이도")
    private HashMap<String, String> topics;

    @ApiModelProperty(example = "매주 화, 수 6시")
    @ApiParam(value = "작업 시간")
    private String schedule;

    @ApiModelProperty(example = "2020-05-22")
    @ApiParam(value = "스터디 마감 예정일")
    private LocalDate period;

    @ApiModelProperty(example = "경기")
    @ApiParam(value = "지역")
    private String city;

    @ApiModelProperty(example = "{\"id\": 3, \"name\": \"SSAFY\"}")
    @ApiParam(value = "소속된 클럽 정보")
    private ClubSimpleInfoResponseDto currentClub;

    @ApiModelProperty(example = "[{\"clubId\": 1, \"clubName\": \"첫 클럽\"}, {\"clubId\": 2, \"clubName\": \"두번째 클럽\"}]")
    @ApiParam(value = "소유자가 속해있는 클럽 목록")
    private List<ClubSimpleInfoResponseDto> clubs;

    @ApiModelProperty(example = "Git 매칭 프로젝트입니다.")
    @ApiParam(value = "프로젝트 소개")
    private String bio;

    @ApiModelProperty(example = "3")
    @ApiParam(value = "현재 인원")
    private int memberCount;

    @ApiModelProperty(example = "3")
    @ApiParam(value = "최대 인원")
    private int maxCount;

    @ApiModelProperty(example = "클럽 멤버에게만 공개")
    @ApiParam(value = "공개 범위")
    private String publicScope;

    @ApiModelProperty(example = "모집 중")
    @ApiParam(value = "모집 상태")
    private String recruitmentState;

    public static StudyInfoForUpdateResponseDto of(Study study,
        HashMap<String, String> topics, List<ClubSimpleInfoResponseDto> clubs){
        return StudyInfoForUpdateResponseDto.builder()
            .id(study.getId())
            .name(study.getName())
            .studyProgressState(study.getStudyProgressState().getState())
            .coverPicUri(
                (study.getCoverPic() == null) ? null : study.getCoverPic().getDownload_uri())
            .topics(topics)
            .schedule(study.getSchedule())
            .period(study.getPeriod())
            .city(study.getCity().toString())
            .currentClub((study.getClub() == null) ? null : ClubSimpleInfoResponseDto.from(
                study.getClub()))
            .clubs(clubs)
            .bio(study.getBio())
            .memberCount(study.getMemberCount())
            .maxCount(study.getMaxCount())
            .publicScope(study.getPublicScope().getState())
            .recruitmentState(study.getRecruitmentState().getState())
            .build();
    }
}
