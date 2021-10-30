package com.ssafy.match.group.club.dto.response;

import com.ssafy.match.group.club.entity.Club;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ClubInfoForSelectResponseDto {

    private Long id;

    private String name;

    public static ClubSimpleInfoResponseDto from(Club club) {
        return ClubSimpleInfoResponseDto.builder()
            .id(club.getId())
            .name(club.getName())
            .build();
    }
}
