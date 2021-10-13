package com.ssafy.match.member.dto.response;


import com.ssafy.match.member.entity.Member;
import lombok.*;

@Data
public class MemberBasicinfoResponseDto {
    private String coverpic_uri;
    private String nickname;
    private String name;
    private String city;
    private String bio;

    public static MemberBasicinfoResponseDto of(Member member) {
        return MemberBasicinfoResponseDto.builder()
                .nickname(member.getNickname())
                .name(member.getName())
                .city(member.getCity())
                .bio(member.getBio())
                .build();
    }

    @Builder
    public MemberBasicinfoResponseDto(String nickname, String name, String city, String bio) {
        this.nickname = nickname;
        this.name = name;
        this.city = city;
        this.bio = bio;
    }
}