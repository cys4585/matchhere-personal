package com.ssafy.match.group.club.dto.response;

import com.ssafy.match.group.club.entity.Club;
import lombok.Getter;

@Getter
public class ClubSimpleInfoResponseDto {

    private Long id;
    private String name;

    public ClubSimpleInfoResponseDto(Club club) {
        this.id = club.getId();
        this.name = club.getName();
    }
}
