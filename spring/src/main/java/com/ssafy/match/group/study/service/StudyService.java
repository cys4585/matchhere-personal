package com.ssafy.match.group.study.service;

import com.ssafy.match.member.entity.Member;
import com.ssafy.match.group.study.dto.request.StudyApplicationRequestDto;
import com.ssafy.match.group.study.dto.request.StudyCreateRequestDto;
import com.ssafy.match.group.study.dto.request.StudyUpdateRequestDto;
import com.ssafy.match.group.study.dto.response.InfoForApplyStudyFormResponseDto;
import com.ssafy.match.group.study.dto.response.StudyFormInfoResponseDto;
import com.ssafy.match.group.study.dto.response.StudyInfoForCreateResponseDto;
import com.ssafy.match.group.study.dto.response.StudyInfoForUpdateResponseDto;
import com.ssafy.match.group.study.dto.response.StudyInfoResponseDto;
import com.ssafy.match.group.study.entity.Study;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

public interface StudyService {

    StudyInfoForCreateResponseDto getInfoForCreate();

    StudyInfoResponseDto create(StudyCreateRequestDto dto);

    StudyInfoResponseDto update(Long studyId, StudyUpdateRequestDto dto) ;

    HttpStatus delete(Long studyId);

//    Page<StudyInfoResponseDto> getAllStudy(Pageable pageable);

    StudyInfoResponseDto getOneStudy(Long studyId);

    StudyInfoForUpdateResponseDto getInfoForUpdateStudy(Long studyId);

    void addMember(Study study, Member member);

    HttpStatus removeMember(Long studyId);

    InfoForApplyStudyFormResponseDto getInfoForApply(Long studyId);

    HttpStatus applyStudy(Long studyId, StudyApplicationRequestDto dto);

    List<StudyFormInfoResponseDto> getAllStudyForm(Long studyId);

    List<StudyFormInfoResponseDto> getAllFormByStudyNickname(Long studyId, String nickname);

    StudyFormInfoResponseDto getOneStudyForm(Long studyId, Long memberId);

    HttpStatus approval(Long studyId, Long memberId);

    HttpStatus reject(Long studyId, Long memberId);

}
