package com.ssafy.match.group.dto;

import com.ssafy.match.member.entity.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDto {

    private Long id;
    private String name;
    private String nickname;

    private String email;

    @ApiModelProperty(name = "fileDownloadUri", example = "http://localhost:8080/api/downloadFile/97534f05-7e7f-425d-ac3e-aae8acee8a42")
    private String fileDownloadUri;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.fileDownloadUri = (member.getCover_pic() == null) ? null : member.getCover_pic().getDownload_uri();
    }
}
