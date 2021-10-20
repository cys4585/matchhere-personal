package com.ssafy.match.group.project.entity;

import com.ssafy.match.common.entity.City;
import com.ssafy.match.common.entity.GroupCity;
import com.ssafy.match.common.entity.ProjectProgressState;
import com.ssafy.match.common.entity.PublicScope;
import com.ssafy.match.common.entity.RecruitmentState;
import com.ssafy.match.common.exception.CustomException;
import com.ssafy.match.common.exception.ErrorCode;
import com.ssafy.match.file.entity.DBFile;
import com.ssafy.match.group.club.entity.Club;
import com.ssafy.match.group.project.dto.request.ProjectCreateRequestDto;
import com.ssafy.match.group.project.dto.request.ProjectUpdateRequestDto;
import com.ssafy.match.member.entity.Member;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "matching.project")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_pic")
    private DBFile coverPic;
    @Enumerated(EnumType.STRING)
    @Column(name = "project_progress_state")
    private ProjectProgressState projectProgressState;
    @Enumerated(EnumType.STRING)
    @Column(name = "public_scope")
    private PublicScope publicScope;
    @Enumerated(EnumType.STRING)
    @Column(name = "recruitment_state")
    private RecruitmentState recruitmentState;
    @Column(name = "view_count")
    private int viewCount;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "modify_date")
    private LocalDateTime modifyDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id")
    private Member member;
    private String schedule;
    private LocalDate period;

    @Column(name = "developer_count")
    private int developerCount;
    @Column(name = "developer_max_count")
    private int developerMaxCount;
    @Column(name = "apply_developer")
    private Boolean applyDeveloper;

    @Column(name = "planner_count")
    private int plannerCount;
    @Column(name = "planner_max_count")
    private int plannerMaxCount;
    @Column(name = "apply_planner")
    private Boolean applyPlanner;

    @Column(name = "designer_count")
    private int designerCount;
    @Column(name = "designer_max_count")
    private int designerMaxCount;
    @Column(name = "apply_designer")
    private Boolean applyDesigner;

    @Enumerated(EnumType.STRING)
    private GroupCity city;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;
    private String bio;
    @Column(name = "is_active")
    private Boolean isActive;

    public void plusViewCount(){
        this.viewCount++;
    }
    public void plusDeveloper() {
        if(Boolean.FALSE.equals(this.applyDeveloper)){
            throw new CustomException(ErrorCode.DEVELOPER_COUNT_OVER);
        }
        this.developerCount++;
    }

    public void plusPlanner() {
        if(Boolean.FALSE.equals(this.applyPlanner)){
            throw new CustomException(ErrorCode.PLANNER_COUNT_OVER);
        }
        this.plannerCount++;
    }

    public void plusDesigner() {
        if(Boolean.FALSE.equals(this.applyDesigner)){
            throw new CustomException(ErrorCode.DESIGNER_COUNT_OVER);
        }
        this.designerCount++;
    }

    public void minusDeveloper() {
        if(this.developerCount == 0){
            throw new CustomException(ErrorCode.DEVELOPER_COUNT_BELOW_ZERO);
        }
        this.developerCount--;
    }

    public void minusPlanner() {
        if(this.plannerCount == 0){
            throw new CustomException(ErrorCode.PLANNER_COUNT_BELOW_ZERO);
        }
        this.plannerCount--;
    }

    public void minusDesigner() {
        if(this.designerCount == 0){
            throw new CustomException(ErrorCode.DESIGNER_COUNT_BELOW_ZERO);
        }
        this.designerCount--;
    }

    public void setDeveloperMaxCount(int count){
        if(this.developerCount > count){
            throw new CustomException(ErrorCode.DEVELOPER_COUNT_MORE_THAN_MAX);
        }
        if(this.developerCount == count){
            this.applyDeveloper = false;
        }
        this.developerMaxCount = count;
    }

    public void setPlannerMaxCount(int count){
        if(this.plannerCount > count){
            throw new CustomException(ErrorCode.PLANNER_COUNT_MORE_THAN_MAX);
        }
        if(this.plannerCount == count){
            this.applyPlanner = false;
        }
        this.plannerMaxCount = count;
    }

    public void setDesignerMaxCount(int count){
        if(this.designerCount > count){
            throw new CustomException(ErrorCode.DESIGNER_COUNT_MORE_THAN_MAX);
        }
        if(this.designerCount == count){
            this.applyDesigner = false;
        }
        this.designerMaxCount = count;
    }

    public void addRole(String str){
        if(str.equals("기획자")){
            this.plusPlanner();
        }else if(str.equals("개발자")){
            this.plusDeveloper();
        }else if(str.equals("디자이너")){
            this.plusDesigner();
        }else{
            throw new CustomException(ErrorCode.ROLE_NOT_FOUND);
        }

    }

    public void setMember(Member member){
        this.member = member;
    }

    public void removeRole(String str){
        if(str.equals("기획자")){
            this.minusPlanner();
        }else if(str.equals("개발자")){
            this.minusDeveloper();
        }else if(str.equals("디자이너")){
            this.minusDesigner();
        }else{
            throw new CustomException(ErrorCode.ROLE_NOT_FOUND);
        }
    }

    public void setIsActive(Boolean active){
        this.isActive = active;
    }

    public void update(ProjectUpdateRequestDto dto, Club club, DBFile coverPic) {
        this.coverPic = coverPic;
        this.name = dto.getName();
        this.projectProgressState = ProjectProgressState.from(dto.getProjectProgressState());
        this.publicScope = PublicScope.from(dto.getPublicScope());
        this.recruitmentState = RecruitmentState.from(dto.getRecruitmentState());
        this.modifyDate = LocalDateTime.now();
        this.schedule = dto.getSchedule();
        this.period = dto.getPeriod();
        setDeveloperMaxCount(dto.getDeveloperMaxCount());
        setPlannerMaxCount(dto.getPlannerMaxCount());
        setDesignerMaxCount(dto.getDesignerMaxCount());
        this.city = GroupCity.from(dto.getCity());
        this.club = club;
        this.bio = dto.getBio();
    }

    public Project(ProjectCreateRequestDto dto, Club club, DBFile coverPic, Member member) {
        this.coverPic = coverPic;
        this.name = dto.getName();
        this.projectProgressState = ProjectProgressState.from(dto.getProjectProgressState());
        this.publicScope = PublicScope.from(dto.getPublicScope());
        this.recruitmentState = RecruitmentState.from(dto.getRecruitmentState());
        this.viewCount = 0;
        this.createDate = LocalDateTime.now();
        this.modifyDate = LocalDateTime.now();
        this.member = member;
        this.schedule = dto.getSchedule();
        this.period = dto.getPeriod();
        this.developerCount = 0;
        this.developerMaxCount = dto.getDeveloperMaxCount();
        this.applyDeveloper = dto.getDeveloperMaxCount() != 0;
        this.plannerCount = 0;
        this.plannerMaxCount = dto.getPlannerMaxCount();
        this.applyPlanner = (dto.getPlannerMaxCount() != 0);
        this.designerCount = 0;
        this.designerMaxCount = dto.getDesignerMaxCount();
        this.applyDesigner = dto.getDesignerMaxCount() != 0;
        this.city = GroupCity.from(dto.getCity());
        this.club = club;
        this.bio = dto.getBio();
        this.isActive = true;
    }
}