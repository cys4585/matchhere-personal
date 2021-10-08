package com.ssafy.match.group.dto.project.request;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import java.time.LocalDateTime;
import javax.persistence.Column;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectApplicationRequestDto {

    @ApiModelProperty(name = "nickname", example = "BJP")
    private String nickname;

    @ApiModelProperty(name = "city", example = "서울")
    private String city;

    @ApiModelProperty(name = "position", example = "프론트")
    private String position;

    @ApiModelProperty(name = "role", example = "개발자")
    private String role;

    @ApiModelProperty(name = "git", example = "BEOMKING")
    private String git;

    @ApiModelProperty(name = "twitter", example = "twitter.com")
    private String twitter;

    @ApiModelProperty(name = "facebook", example = "facebook.com")
    private String facebook;

    @ApiModelProperty(name = "backjoon", example = "qjawlsqjacks")
    private String backjoon;

    @ApiModelProperty(name = "bio", example = "설명ㅇㅇㅇㅇ")
    private String bio;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @ApiModelProperty(name = "uuid", example = "3fads23-fdfd13-23d2")
    private String uuid;

}