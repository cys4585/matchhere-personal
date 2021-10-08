package com.ssafy.match.member.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberUpdateResponseDto {
    private Long id;

    public static MemberUpdateResponseDto of(Long id) {
        return new MemberUpdateResponseDto(id);
    }
}
