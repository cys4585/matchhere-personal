package com.ssafy.match.group.club.service;

import com.ssafy.match.file.dto.DBFileDto;
import com.ssafy.match.group.club.dto.request.ClubApplicationRequestDto;
import com.ssafy.match.group.club.dto.request.ClubCreateRequestDto;
import com.ssafy.match.group.club.dto.request.ClubUpdateRequestDto;
import com.ssafy.match.group.club.dto.response.ClubFormInfoResponseDto;
import com.ssafy.match.group.club.dto.response.ClubFormSimpleInfoResponseDto;
import com.ssafy.match.group.club.dto.response.ClubInfoForUpdateResponseDto;
import com.ssafy.match.group.club.dto.response.ClubInfoResponseDto;
import com.ssafy.match.group.club.dto.response.ClubSimpleInfoResponseDto;
import com.ssafy.match.group.club.entity.Club;
import com.ssafy.match.member.dto.MemberSimpleInfoResponseDto;
import com.ssafy.match.member.entity.Member;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

public interface ClubService {

    //클럽 생성
    ClubInfoResponseDto create(ClubCreateRequestDto dto);

    // 클럽 수정
    ClubInfoResponseDto update(Long clubId, ClubUpdateRequestDto dto);

    // 클럽 수정시 사진 변경
    DBFileDto changeCoverPic(Long clubId, String uuid);

    DBFileDto getCoverPicUri(Long clubId);

    // 조회 수 증가
    HttpStatus plusViewCount(Long clubId);

    // 권한 변경
    HttpStatus changeAuthority(Long clubId, Long memberId, String authority);

    HttpStatus delete(Long clubId);

    // 클럽 전체 조회
    Page<ClubSimpleInfoResponseDto> getAllClub(Pageable pageable);

    // 클럽 상세 조회
    ClubInfoResponseDto getOneClub(Long clubId);

    // 현재 클럽 간편 정보 리턴
    ClubSimpleInfoResponseDto getOneSimpleClub(Long clubId);

    String getMemberAuthority(Long clubId);

    // 클럽 구성원 리스트
    List<MemberSimpleInfoResponseDto> getMembersInClub(Long clubId);

    ClubInfoForUpdateResponseDto getInfoForUpdateClub(Long clubId);

    void addMember(Club club, Member member);

    HttpStatus removeMe(Long clubId);

    HttpStatus removeMember(Long clubId, Long memberId);

    boolean checkCanApply(Long clubId);

    ClubFormInfoResponseDto applyClub(Long clubId, ClubApplicationRequestDto dto);

    // 모든 클럽 신청서 조회
    List<ClubFormSimpleInfoResponseDto> getAllClubForm(Long clubId);

//    List<ClubFormInfoResponseDto> getAllFormByClubNickname(Long clubId, String nickname);

    ClubFormInfoResponseDto getOneClubForm(Long clubId, Long memberId);

    HttpStatus approval(Long clubId, Long memberId);

    HttpStatus reject(Long clubId, Long memberId);
}
