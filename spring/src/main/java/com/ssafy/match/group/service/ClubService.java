package com.ssafy.match.group.service;

import com.ssafy.match.group.dto.club.request.ClubApplicationRequestDto;
import com.ssafy.match.group.dto.club.request.ClubCreateRequestDto;
import com.ssafy.match.group.dto.club.request.ClubUpdateRequestDto;
import com.ssafy.match.group.dto.club.response.ClubFormInfoResponseDto;
import com.ssafy.match.group.dto.club.response.ClubInfoResponseDto;
import com.ssafy.match.group.dto.club.response.InfoForApplyClubFormResponseDto;
import com.ssafy.match.group.dto.study.request.StudyApplicationRequestDto;
import com.ssafy.match.group.dto.study.request.StudyCreateRequestDto;
import com.ssafy.match.group.dto.study.request.StudyUpdateRequestDto;
import com.ssafy.match.group.dto.study.response.InfoForApplyStudyFormResponseDto;
import com.ssafy.match.group.dto.study.response.StudyFormInfoResponseDto;
import com.ssafy.match.group.dto.study.response.StudyInfoForCreateResponseDto;
import com.ssafy.match.group.dto.study.response.StudyInfoForUpdateResponseDto;
import com.ssafy.match.group.dto.study.response.StudyInfoResponseDto;
import com.ssafy.match.group.entity.study.Study;
import com.ssafy.match.member.entity.Member;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

public interface ClubService {

    Long create(ClubCreateRequestDto dto) throws Exception;

    HttpStatus update(Long clubId, ClubUpdateRequestDto dto) throws Exception;

    HttpStatus delete(Long clubId) throws Exception;

    Page<ClubInfoResponseDto> getAllClub(Pageable pageable);

    ClubInfoResponseDto getOneClub(Long clubId) throws Exception;

    HttpStatus removeMember(Long studyId) throws Exception;

    InfoForApplyClubFormResponseDto getInfoForApply(Long clubId) throws Exception;

    HttpStatus applyClub(Long clubId, ClubApplicationRequestDto dto) throws Exception;

    public List<ClubFormInfoResponseDto> getAllClubForm(Long clubId) throws Exception;

    List<ClubFormInfoResponseDto> getAllFormByClubNickname(Long clubId, String nickname) throws Exception;

    ClubFormInfoResponseDto getOneClubForm(Long clubId, Long memberId) throws Exception;

    HttpStatus approval(Long clubId, Long memberId) throws Exception;

    HttpStatus reject(Long clubId, Long memberId) throws Exception;

}
