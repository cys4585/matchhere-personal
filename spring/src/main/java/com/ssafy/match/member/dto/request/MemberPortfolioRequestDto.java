package com.ssafy.match.member.dto.request;


import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberPortfolioRequestDto {
    @ApiModelProperty(name = "poltfolio_uuid", example = "uuid")
    @ApiParam(value = "포트폴리오 uuid", required = false)
    private String portfolio_uuid;

    @ApiModelProperty(name = "portfolio_uri", example = "https://naver.com")
    @ApiParam(value = "포트폴리오 uri", required = false)
    private String portfolio_uri;

//    @ApiModelProperty(name = "snsHashMap", example = "{\"github\":\"github id\",\"twitter\":\"twitter id\"}")
//    @ApiParam(value = "sns 종류/계정", required = false)
//    private HashMap<String, String> snsHashMap;
}
