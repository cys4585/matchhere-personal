package com.ssafy.match.group.project.service;

import com.ssafy.match.group.project.dto.response.ProjectFormSimpleInfoResponseDto;
import com.ssafy.match.group.project.dto.response.ProjectSimpleInfoResponseDto;
import com.ssafy.match.member.entity.Member;
import com.ssafy.match.group.project.dto.request.ProjectApplicationRequestDto;
import com.ssafy.match.group.project.dto.response.InfoForApplyProjectFormResponseDto;
import com.ssafy.match.group.project.dto.response.ProjectFormInfoResponseDto;
import com.ssafy.match.group.project.dto.request.ProjectCreateRequestDto;
import com.ssafy.match.group.project.dto.response.ProjectInfoForCreateResponseDto;
import com.ssafy.match.group.project.dto.response.ProjectInfoForUpdateResponseDto;
import com.ssafy.match.group.project.dto.response.ProjectInfoResponseDto;
import com.ssafy.match.group.project.dto.request.ProjectUpdateRequestDto;
import com.ssafy.match.group.project.entity.Project;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;

public interface ProjectService {

    ProjectInfoForCreateResponseDto getInfoForCreate();

    Long create(ProjectCreateRequestDto dto);

    HttpStatus update(Long projectId, ProjectUpdateRequestDto dto);

    HttpStatus delete(Long projectId);

    List<ProjectSimpleInfoResponseDto> getAllProject(Pageable pageable);

//    List<ProjectSimpleInfoResponseDto> getRecommendationProject(Pageable pageable);

    ProjectInfoResponseDto getOneProject(Long projectId);

    ProjectInfoForUpdateResponseDto getInfoForUpdateProject(Long projectId);

    void addMember(Project project, Member member, String role);

    HttpStatus removeMe(Long projectId);

    HttpStatus removeMember(Long projectId, Long memberId);

    HttpStatus changeRole(Long projectId, Long memberId, String role);

    HttpStatus changeAuthority(Long projectId, Long memberId, String authority);

    HttpStatus checkCanApply(Long projectId);

    HttpStatus applyProject(Long projectId, ProjectApplicationRequestDto dto);

    Slice<ProjectFormSimpleInfoResponseDto> allProjectForm(Long projectId, Pageable pageable);

    ProjectFormInfoResponseDto oneProjectForm(Long projectId, Long memberId);

    HttpStatus approval(Long projectId, Long memberId);

    HttpStatus reject(Long projectId, Long memberId);
}
