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

    StudyInfoForCreateResponseDto getInfoForCreate() throws Exception;

    Long create(StudyCreateRequestDto dto) throws Exception;

    HttpStatus update(Long studyId, StudyUpdateRequestDto dto) throws Exception;

    HttpStatus delete(Long studyId) throws Exception;

//    Page<StudyInfoResponseDto> getAllStudy(Pageable pageable);

    StudyInfoResponseDto getOneStudy(Long studyId) throws Exception;

    StudyInfoForUpdateResponseDto getInfoForUpdateStudy(Long studyId) throws Exception;

    void addMember(Study study, Member member) throws Exception;

    HttpStatus removeMember(Long studyId) throws Exception;

    InfoForApplyStudyFormResponseDto getInfoForApply(Long studyId) throws Exception;

    HttpStatus applyStudy(Long studyId, StudyApplicationRequestDto dto) throws Exception;

    List<StudyFormInfoResponseDto> getAllStudyForm(Long studyId) throws Exception;

    List<StudyFormInfoResponseDto> getAllFormByStudyNickname(Long studyId, String nickname) throws Exception;

    StudyFormInfoResponseDto getOneStudyForm(Long studyId, Long memberId) throws Exception;

    HttpStatus approval(Long studyId, Long memberId) throws Exception;

    HttpStatus reject(Long studyId, Long memberId) throws Exception;

}
