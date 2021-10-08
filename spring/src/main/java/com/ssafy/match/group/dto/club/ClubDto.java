package com.ssafy.match.group.dto.club;

import com.ssafy.match.group.entity.club.Club;
import lombok.Getter;

@Getter
public class ClubDto {

    private Long id;
    private String name;

    public ClubDto(Club club) {
        this.id = club.getId();
        this.name = club.getName();
    }
}
