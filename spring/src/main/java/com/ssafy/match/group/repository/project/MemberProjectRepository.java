package com.ssafy.match.group.repository.project;

import com.ssafy.match.db.entity.Status;
import com.ssafy.match.member.entity.Member;
import com.ssafy.match.group.entity.project.CompositeMemberProject;
import com.ssafy.match.group.entity.project.MemberProject;
import com.ssafy.match.group.entity.project.Project;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberProjectRepository extends JpaRepository<MemberProject, CompositeMemberProject> {

    // 특정 프로젝트에 속한 멤버의 관계 정보
    @Query(value = "select mp from matching.member_project mp "
        + "where mp.compositeMemberProject.project = :project and mp.isActive = true")
    List<MemberProject> findMemberRelationInProject(@Param("project") Project project);

    // 특정 프로젝트에 속한 멤버의 정보
    @Query(value = "select mp.compositeMemberProject.member from matching.member_project mp "
        + "where mp.compositeMemberProject.project = :project and mp.isActive = true")
    List<Member> findMemberInProject(@Param("project") Project project);

    // 특정 프로젝트의 특정 멤버의 관계 정보
    @Query(value = "select mp from matching.member_project mp "
        + "where mp.compositeMemberProject.project = :project and mp.compositeMemberProject.member = :member")
    MemberProject findMemberProject(@Param("project") Project project, @Param("member") Member member);

    // 특정 프로젝트의 활성화 되어있는 개발자들의 닉네임 정보
    @Query(value = "select mp.compositeMemberProject.member.nickname "
        + "from matching.member_project mp "
        + "where mp.compositeMemberProject.project = :project and mp.role = :role and mp.isActive = true")
    List<String> findMemberNickname(@Param("project") Project project, @Param("role") String role);

    // 특정 멤버가 가지고 있는 활성화 프로젝트
    @Query(value = "select mp.compositeMemberProject.project from matching.member_project mp "
        + "where mp.compositeMemberProject.member = :member and mp.isActive = true")
    List<Project> projectInMember(@Param("member") Member member);

    @Query(value = "select mp.compositeMemberProject.member from matching.member_project mp "
            + "where mp.compositeMemberProject.project.id = :id and mp.isActive = true")
    List<Member> findMemberByProjectId(@Param("id") Long id);

    @Query(value = "select mp.compositeMemberProject.project from matching.member_project mp "
            + "where mp.compositeMemberProject.member = :member and mp.isActive = true and mp.compositeMemberProject.project.status <> :status")
    List<Project> getProjectsByMemberAndStatus(@Param("member") Member member, @Param("status") Enum<Status> status);
}