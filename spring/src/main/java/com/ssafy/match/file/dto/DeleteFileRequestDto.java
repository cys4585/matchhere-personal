package com.ssafy.match.file.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ApiModel(value = "파일 삭제 요청", description = "사진, 포폴 변경할 때 기존것을 삭제하기 위한 Request Dto Class")
public class DeleteFileRequestDto {

    @ApiModelProperty( example = "http://localhost:8080/api/downloadFile/97534f05-7e7f-425d-ac3e-aae8acee8a42")
    private String fileDownloadUri;

}
