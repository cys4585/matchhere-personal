package com.ssafy.match.file.dto;

import com.ssafy.match.file.entity.DBFile;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class DBFileSimpleDto {

    @ApiModelProperty(example = "http://localhost:8080/api/downloadFile/97534f05-7e7f-425d-ac3e-aae8acee8a42")
    @ApiParam(value = "파일 다운로드 Uri")
    private String coverPicUri;

    public static DBFileSimpleDto from(DBFile coverPic){
        if(coverPic == null) {
            return null;
        }
        return DBFileSimpleDto.builder()
            .coverPicUri(coverPic.getDownload_uri())
            .build();
    }
}
