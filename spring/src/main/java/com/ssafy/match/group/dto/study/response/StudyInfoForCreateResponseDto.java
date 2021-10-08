package com.ssafy.match.group.dto.study.response;

import com.ssafy.match.group.dto.club.ClubDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import java.util.List;
import lombok.Builder;
import lombok.Getter;


@ApiModel(value = "스터디 생성을 위해 필요한 정보", description = "호스트가 가진 클럽 정보 Response Dto Class")
@Getter
@Builder
public class StudyInfoForCreateResponseDto {

    @ApiModelProperty(name = "hostClub", example = "[{\"id\": 1, \"name\": \"클럽1\"}, {\"id\": 2, \"name\": \"클럽2\"}]")
    @ApiParam(value = "스터디를 생성하려는 멤버의 클럽 id, name 정보 리스트", required = true)
    private List<ClubDto> hostClub;

}
