package com.ssafy.match.group.entity.project;

import com.ssafy.match.db.entity.City;
import com.ssafy.match.group.entity.club.Club;
import com.ssafy.match.member.entity.Member;
import com.ssafy.match.db.entity.Status;
import com.ssafy.match.file.entity.DBFile;
import com.ssafy.match.group.dto.project.request.ProjectCreateRequestDto;
import com.ssafy.match.group.dto.project.request.ProjectUpdateRequestDto;
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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "matching.project")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String schedule;
    private int period;
    private String bio;
    @Column(name = "developer_count")
    private int developerCount;
    @Column(name = "developer_max_count")
    private int developerMaxCount;
    @Column(name = "planner_count")
    private int plannerCount;
    @Column(name = "planner_max_count")
    private int plannerMaxCount;
    @Column(name = "designer_count")
    private int designerCount;
    @Column(name = "designer_max_count")
    private int designerMaxCount;

    @Enumerated(EnumType.STRING)
    private City city;
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "is_public")
    private Boolean isPublic;
    @Column(name = "is_participate")
    private Boolean isParticipate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id")
    private Member member;

    @Column(name = "host_role")
    private String hostRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_pic")
    private DBFile dbFile;

    public void plusDeveloper(){
        this.developerCount++;
    }
    public void plusPlanner(){
        this.plannerCount++;
    }
    public void plusDesigner(){
        this.designerCount++;
    }
    public void minusDeveloper(){
        this.developerCount--;
    }
    public void minusPlanner(){
        this.plannerCount--;
    }
    public void minusDesigner(){
        this.designerCount--;
    }

    public void setMember(Member member){
        this.member = member;
    }

    public void setClub(Club club){
        if(club == null) return;
        this.club = club;
    }

    public void setDBFile(DBFile dbFile){
        if(dbFile == null) return;
        this.dbFile = dbFile;
    }

    public void update(ProjectUpdateRequestDto dto, Club club, DBFile dbFile) {
        this.name = dto.getName();
        this.schedule = dto.getSchedule();
        this.period = dto.getPeriod();
        this.bio = dto.getBio();
        this.designerMaxCount = dto.getDesignerMaxCount();
        this.developerMaxCount = dto.getDeveloperMaxCount();
        this.plannerMaxCount = dto.getPlannerMaxCount();
        this.city = City.from(dto.getCity());
        this.modifyDate = LocalDateTime.now();
        this.status = Status.from(dto.getStatus());
        this.isPublic = dto.getIsPublic();
        this.isParticipate = dto.getIsParticipate();
        setDBFile(dbFile);
        setClub(club);
    }

    public Project(ProjectCreateRequestDto dto, Club club, DBFile dbFile) {
        this.name = dto.getName();
        this.schedule = dto.getSchedule();
        this.period = dto.getPeriod();
        this.bio = dto.getBio();
        this.developerCount = 0;
        this.developerMaxCount = dto.getDeveloperMaxCount();
        this.plannerCount = 0;
        this.plannerMaxCount = dto.getPlannerMaxCount();
        this.designerCount = 0;
        this.designerMaxCount = dto.getDesignerMaxCount();
        this.city = City.from(dto.getCity());
        this.status = Status.모집;
        this.createDate = LocalDateTime.now();
        this.modifyDate = LocalDateTime.now();
        this.isActive = true;
        this.isPublic = dto.getIsPublic();
        this.isParticipate = true;
        setDBFile(dbFile);
        setClub(club);
    }
}