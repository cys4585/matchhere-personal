package com.ssafy.match.member.dto.response;


import com.ssafy.match.member.dto.request.MemberBasicInfoRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberBasicinfoResponseDto {
    private String coverpic_uri;
    private String nickname;
    private String name;
    private String city;
    private String bio;

//    public static MemberBasicinfoResponseDto of(String coverpic_uri, String nickname, String name, String city, String bio) {
//        return new MemberBasicinfoResponseDto(coverpic_uri, nickname, name, city, bio);
//    }

    public static MemberBasicinfoResponseDto of(MemberBasicInfoRequestDto memberBasicinfoRequestDto) {
        return new MemberBasicinfoResponseDto(memberBasicinfoRequestDto.getCoverpic_uuid(), memberBasicinfoRequestDto.getNickname(), memberBasicinfoRequestDto.getName(), memberBasicinfoRequestDto.getCity(), memberBasicinfoRequestDto.getBio());
    }
}