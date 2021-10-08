package com.ssafy.match.member.dto;

import com.ssafy.match.db.entity.Authority;
import com.ssafy.match.member.entity.Member;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {

    @ApiModelProperty(name = "email", example = "my_email@gmail.com")
    @ApiParam(value = "이메일", required = true)
    @Email
    @NotEmpty
    private String email;

    @ApiModelProperty(name = "password", example = "mypassword")
    @ApiParam(value = "비밀번호", required = true)
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,}$",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    @NotEmpty
    private String password;

    private LocalDateTime create_date;

    @ApiModelProperty(name = "name", example = "문일민")
    @ApiParam(value = "이름", required = true)
    @Pattern(regexp = "^[가-힣]{1,8}|[a-zA-Z]{2,8}$")
    @NotEmpty
    private String name;

    @ApiModelProperty(name = "nickname", example = "별명")
    @ApiParam(value = "별명", required = true)
    @Length(min = 2, max=10)
    @NotEmpty
    private String nickname;

    @ApiModelProperty(name = "tel", example = "010-1234-4567")
    @ApiParam(value = "전화번호", required = false)
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
    private String tel;

    @ApiModelProperty(name = "bio", example = "let me introduce")
    @ApiParam(value = "자기소개", required = false)
    private String bio;

    @ApiModelProperty(name = "cover_pic", example = "3fads23-fdfd13-23d2")
    @ApiParam(value = "사진 데이터", required = false)
    private String cover_pic;

    @ApiModelProperty(name = "portfolio_uuid", example = "3fads23-fdfd13-23d2")
    @ApiParam(value = "포트폴리오 데이터", required = false)
    private String portfolio_uuid;

    @ApiModelProperty(name = "city", example = "부산")
    @ApiParam(value = "도시", required = false)
    private String city;

    private Boolean banned;

    @ApiModelProperty(name = "position", example = "개발자")
    @ApiParam(value = "역할", required = false)
    private String position;

    private Boolean is_active;

    @ApiModelProperty(name = "portfolio_uri", example = "https://naver.com")
    @ApiParam(value = "포트폴리오 uri", required = false)
    private String portfolio_uri;

    @ApiModelProperty(name = "expTechList", example = "[\"python\",\"java\"]")
    @ApiParam(value = "experienced 기술 리스트", required = false)
    private List<String> expTechList;

    @ApiModelProperty(name = "beginTechList", example = "[\"python\",\"java\"]")
    @ApiParam(value = "beginner 기술 리스트", required = false)
    private List<String> beginTechList;

    @ApiModelProperty(name = "dpositionList", example = "[\"frontend\",\"devops\"]")
    @ApiParam(value = "세부 포지션", required = false)
    private List<String> dpositionList;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .create_date(LocalDateTime.now())
                .name(name)
                .nickname(nickname)
                .tel(tel)
                .bio(bio)
                .city(city)
                .banned(Boolean.FALSE)
                .position(position)
                .is_active(Boolean.TRUE)
                .authority(Authority.ROLE_USER)
                .portfolio_uri(portfolio_uri)
                .build();
    }
//    public MemberExperiencedTechstack toMemberExperiencedTechstack(Member member, Techstack techstack) {
//        CompositeMemberTechstack compositeMemberTechstack = toCompositeMemberTechstack(member, techstack);
//        return MemberExperiencedTechstack.builder()
//                .compositeMemberTechstack(compositeMemberTechstack)
//                .build();
//    }
//    public CompositeMemberTechstack toCompositeMemberTechstack(Member member, Techstack techstack) {
//        return CompositeMemberTechstack
//                .builder()
//                .member(member)
//                .teckstack(techstack)
//                .build();
//    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}