package com.ssafy.match.group.studyboard.board.service;

import com.ssafy.match.group.entity.study.Study;
import com.ssafy.match.group.repository.study.StudyRepository;
import com.ssafy.match.group.studyboard.board.dto.StudyBoardCreateRequestDto;
import com.ssafy.match.group.studyboard.board.dto.StudyBoardInfoDto;
import com.ssafy.match.group.studyboard.board.dto.StudyBoardUpdateDto;
import com.ssafy.match.group.studyboard.board.entity.StudyBoard;
import com.ssafy.match.group.studyboard.board.repository.StudyBoardRepository;
import com.ssafy.match.member.entity.Member;
import com.ssafy.match.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyBoardService {
    private final MemberRepository memberRepository;
    private final StudyBoardRepository studyBoardRepository;
    private final StudyRepository studyRepository;

    @Transactional(readOnly = true)
    public List<StudyBoardInfoDto> getStudyBoards(Long studyId) throws Exception {
        if (!studyRepository.existsById(studyId)) {
            throw new RuntimeException("존재하지 않는 study입니다.");
        }
        Study study = studyRepository.getById(studyId);
        List<StudyBoardInfoDto> studyBoardInfoDtos = studyBoardRepository.findAllByStudy(study).stream()
                .map(StudyBoardInfoDto::of)
                .collect(Collectors.toList());
        return studyBoardInfoDtos;
    }

    @Transactional
    public Integer createBoard(Long studyId, StudyBoardCreateRequestDto studyBoardCreateRequestDto) throws Exception {
        if (!studyRepository.existsById(studyId)) {
            throw new RuntimeException("존재하지 않는 study입니다.");
        }
        Study study = studyRepository.getById(studyId);
        StudyBoard studyBoard = studyBoardCreateRequestDto.toStudyBoard(study);
        StudyBoard ret = studyBoardRepository.save(studyBoard);
        return ret.getId();
    }

    @Transactional
    public Boolean deleteBoard(Integer boardId) throws Exception {
        if (!studyBoardRepository.existsById(boardId)) {
            throw new RuntimeException("존재하지 않는 게시판입니다.");
        }
        studyBoardRepository.delete(studyBoardRepository.getById(boardId));
        return Boolean.TRUE;
    }

    @Transactional
    public Boolean updateBoard(Integer boardId, StudyBoardUpdateDto studyBoardUpdateDto) throws Exception {
        if (!studyBoardRepository.existsById(boardId)) {
            throw new RuntimeException("존재하지 않는 게시판입니다.");
        }
        StudyBoard studyBoard = studyBoardRepository.getById(boardId);
        studyBoard.setName(studyBoardUpdateDto.getName());
        return Boolean.TRUE;
    }

    public Member findMember(Long memberId) throws Exception {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NullPointerException("회원 정보가 없습니다."));
        if (!member.getIs_active()) {
            throw new Exception("삭제된 멤버입니다.");
        }
        return member;
    }
}
