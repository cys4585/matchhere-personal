package com.ssafy.match.member.dto.response;


import com.ssafy.match.member.entity.Member;
import com.ssafy.match.member.entity.MemberSns;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MemberPortfolioResponseDto {
    @ApiModelProperty(name = "portfolio", example = "http://cdn.matchhere.me/path/portfolio.pdf")
    private String portfolio;

    @ApiModelProperty(name = "portfolio_uri", example = "https://naver.com")
    private String portfolio_uri;

    @ApiModelProperty(name = "snsList", example = "[{\"id\":1, \"snsName\":\"github\", \"snsAccount\":\"gitid\"},{\"id\":2, \"snsName\":\"twitter\", \"snsAccount\":\"twitterid\"}]")
    private List<MemberSns> snsList = new ArrayList<>();

    public static MemberPortfolioResponseDto of(Member member) {
        return MemberPortfolioResponseDto.builder()
                .portfolio_uri(member.getPortfolio_uri())
                .build();
    }

    @Builder
    public MemberPortfolioResponseDto(String portfolio_uri) {
        this.portfolio_uri = portfolio_uri;
    }
}
