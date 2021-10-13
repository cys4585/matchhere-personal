package com.ssafy.match.member.dto;

import com.ssafy.match.member.entity.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberSimpleInfoResponseDto {

    private Long id;
    private String name;
    private String nickname;
    private String email;
    @ApiModelProperty(name = "fileDownloadUri", example = "http://localhost:8080/api/downloadFile/97534f05-7e7f-425d-ac3e-aae8acee8a42")
    private String fileDownloadUri;

    public static MemberSimpleInfoResponseDto from(Member member) {
        return MemberSimpleInfoResponseDto.builder()
            .id(member.getId())
            .name(member.getName())
            .nickname(member.getNickname())
            .email(member.getEmail())
            .fileDownloadUri((member.getCover_pic() == null) ? null : member.getCover_pic().getDownload_uri())
            .build();
    }

}
