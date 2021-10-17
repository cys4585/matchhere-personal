package com.ssafy.match.member.dto.response;


import com.ssafy.match.file.dto.DBFileDto;
import com.ssafy.match.member.entity.Member;
import com.ssafy.match.member.entity.MemberSns;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MemberSnsPortfolioResponseDto {
    @ApiModelProperty(name = "portfolio", example = "http://cdn.matchhere.me/path/portfolio.pdf")
    private DBFileDto portfolio;

    @ApiModelProperty(name = "portfolio_uri", example = "https://naver.com")
    private String portfolio_uri;

    @ApiModelProperty(name = "snsList", example = "[{\"id\":1, \"snsName\":\"github\", \"snsAccount\":\"gitid\"},{\"id\":2, \"snsName\":\"twitter\", \"snsAccount\":\"twitterid\"}]")
    private List<MemberSns> snsList = new ArrayList<>();

    public static MemberSnsPortfolioResponseDto of(Member member, List<MemberSns> snsList) {
        return MemberSnsPortfolioResponseDto.builder()
                .portfolio(DBFileDto.of(member.getPortfolio()))
                .portfolio_uri(member.getPortfolio_uri())
                .snsList(snsList)
                .build();
    }

    @Builder
    public MemberSnsPortfolioResponseDto(DBFileDto portfolio, String portfolio_uri, List<MemberSns> snsList) {
        this.portfolio = portfolio;
        this.portfolio_uri = portfolio_uri;
        this.snsList = snsList;
    }
//    @Builder
//    public MemberSnsPortfolioResponseDto(String portfolio_uri) {
//        this.portfolio_uri = portfolio_uri;
//    }
}
