package com.ssafy.match.group.service;

import com.ssafy.match.member.entity.Member;
import com.ssafy.match.group.dto.project.request.ProjectApplicationRequestDto;
import com.ssafy.match.group.dto.project.response.InfoForApplyProjectFormResponseDto;
import com.ssafy.match.group.dto.project.response.ProjectFormInfoResponseDto;
import com.ssafy.match.group.dto.project.request.ProjectCreateRequestDto;
import com.ssafy.match.group.dto.project.response.ProjectInfoForCreateResponseDto;
import com.ssafy.match.group.dto.project.response.ProjectInfoForUpdateResponseDto;
import com.ssafy.match.group.dto.project.response.ProjectInfoResponseDto;
import com.ssafy.match.group.dto.project.request.ProjectUpdateRequestDto;
import com.ssafy.match.group.entity.project.Project;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

public interface ProjectService {

    ProjectInfoForCreateResponseDto getInfoForCreate() throws Exception;

    Long create(ProjectCreateRequestDto dto) throws Exception;

    HttpStatus update(Long projectId, ProjectUpdateRequestDto dto) throws Exception;

    HttpStatus delete(Long projectId) throws Exception;

    Page<ProjectInfoResponseDto> getAllProject(Pageable pageable);

    ProjectInfoResponseDto getOneProject(Long projectId) throws Exception;

    ProjectInfoForUpdateResponseDto getInfoForUpdateProject(Long projectId) throws Exception;

    List<ProjectInfoResponseDto> projectInMember(Long memberId) throws Exception;

    void addMember(Project project, Member member, String role) throws Exception;

    void removeMember(Long projectId) throws Exception;

    void changeRole(Project project, Member member, String role) throws Exception;

    InfoForApplyProjectFormResponseDto getInfoForApply(Long projectId) throws Exception;

    HttpStatus applyProject(Long projectId, ProjectApplicationRequestDto dto) throws Exception;

    List<ProjectFormInfoResponseDto> allProjectForm(Long projectId) throws Exception;

    List<ProjectFormInfoResponseDto> allFormByProjectNickname(Long projectId, String nickname) throws Exception;

    ProjectFormInfoResponseDto oneProjectForm(Long projectId, Long memberId) throws Exception;

    HttpStatus approval(Long projectId, Long memberId) throws Exception;

    HttpStatus reject(Long projectId, Long memberId) throws Exception;
}
