package com.ssafy.match.member.dto.response;


import com.ssafy.match.common.dto.DetailPositionInterface;
import com.ssafy.match.member.dto.MemberTechstackInterface;
import com.ssafy.match.member.dto.MypageResponseDto;
import com.ssafy.match.member.entity.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
public class MemberSkillResponseDto {

    @ApiModelProperty(name = "position", example = "개발자")
    private String position;

    @ApiModelProperty(name = "techList", example = "[{\"name\":\"python\", \"level\":\"상\", \"img_uri\":\"http://cdn.matchhere.me/path/python.png\"}, {\"name\":\"java\", \"level\":\"중\", \"img_uri\":\"http://cdn.matchhere.me/path/java.png\"}]")
    private List<MemberTechstackInterface> techList = new ArrayList<>();

    @ApiModelProperty(name = "dpositionList", example = "[\"프론트엔드\", \"데브옵스\"]")
    private List<DetailPositionInterface> dpositionList = new ArrayList<>();

    public static MemberSkillResponseDto of(Member member) {
        return MemberSkillResponseDto.builder()
                .position(member.getPosition())
                .build();
    }

    @Builder
    public MemberSkillResponseDto(String position) {
        this.position = position;
    }
}
