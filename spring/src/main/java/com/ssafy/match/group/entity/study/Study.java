package com.ssafy.match.group.entity.study;

import com.ssafy.match.db.entity.City;
import com.ssafy.match.group.entity.club.Club;
import com.ssafy.match.member.entity.Member;
import com.ssafy.match.db.entity.Status;
import com.ssafy.match.file.entity.DBFile;
import com.ssafy.match.group.dto.study.request.StudyCreateRequestDto;
import com.ssafy.match.group.dto.study.request.StudyUpdateRequestDto;
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
@Entity(name = "matching.study")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String schedule;
    private int period;
    private String bio;
    @Column(name = "member_count")
    private int memberCount;
    @Column(name = "max_count")
    private int maxCount;

    @Enumerated(EnumType.STRING)
    private City city;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "create_Date")
    private LocalDateTime createDate;
    @Column(name = "modify_Date")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_pic")
    private DBFile dbFile;

    public void addMember(){
        this.memberCount++;
    }

    public void removeMember(){
        this.memberCount--;
    }

    public void setMember(Member member){
        this.member = member;
    }

    public void setClub(Club club){
        if(club == null) {
            this.club = null;
        }
        this.club = club;
    }

    public void setDBFile(DBFile dbFile){
        if(dbFile == null) {
            this.dbFile = null;
        }
        this.dbFile = dbFile;
    }

    public void setMaxCount(int num) throws Exception {
        if(num < 1) {
            throw new Exception("인원은 1명 이상이 되어야합니다.");
        }
        this.maxCount = num;
    }


    public void update(StudyUpdateRequestDto dto) throws Exception {
        this.name = dto.getName();
        this.schedule = dto.getSchedule();
        this.period = dto.getPeriod();
        this.bio = dto.getBio();
        setMaxCount(dto.getMaxCount());
        this.city = City.from(dto.getCity());
        this.modifyDate = LocalDateTime.now();
        this.status = Status.from(dto.getStatus());
        this.isPublic = dto.getIsPublic();
        this.isParticipate = dto.getIsParticipate();
    }

    public Study(StudyCreateRequestDto dto) throws Exception {
        this.name = dto.getName();
        this.schedule = dto.getSchedule();
        this.period = dto.getPeriod();
        this.bio = dto.getBio();
        this.memberCount = 0;
        setMaxCount(dto.getMaxCount());
        this.city = City.from(dto.getCity());
        this.createDate = LocalDateTime.now();
        this.modifyDate = LocalDateTime.now();
        this.status = Status.모집;
        this.isActive = true;
        this.isPublic = dto.getIsPublic();
        this.isParticipate = true;
    }

}