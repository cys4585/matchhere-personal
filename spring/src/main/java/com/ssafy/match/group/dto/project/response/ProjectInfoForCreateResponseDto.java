package com.ssafy.match.group.dto.project.response;

import com.ssafy.match.group.dto.club.ClubDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "프로젝트 생성을 위해 필요한 정보", description = "호스트가 가진 클럽 정보 Response Dto Class")
@Getter
@Setter
@Builder
public class ProjectInfoForCreateResponseDto {

    @ApiModelProperty(name = "hostClub", example = "[{\"id\": 1, \"name\": \"클럽1\"}, {\"id\": 2, \"name\": \"클럽2\"}]")
    @ApiParam(value = "프로젝트를 생성하려는 멤버의 클럽 id, name 정보 리스트", required = true)
    private List<ClubDto> hostClub;

}
