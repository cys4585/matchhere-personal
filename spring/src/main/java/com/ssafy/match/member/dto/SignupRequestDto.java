package com.ssafy.match.member.dto;

import com.ssafy.match.common.annotation.Enum;
import com.ssafy.match.common.entity.Authority;
import com.ssafy.match.common.entity.City;
import com.ssafy.match.member.entity.Member;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {

    @ApiModelProperty(name = "email", example = "my_email@gmail.com")
    @ApiParam(value = "이메일", required = true)
    @Email
    @NotEmpty
    private String email;

    @ApiModelProperty(name = "password", example = "mypassword")
    @ApiParam(value = "비밀번호", required = true)
    @Pattern(regexp="(?=^.{8,255}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&amp;*()_+}{&quot;:;'?/&gt;.&lt;,])(?!.*\\s).*$",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 255자의 비밀번호여야 합니다.")
    @NotEmpty
    private String password;

    @ApiModelProperty(name = "name", example = "문일민")
    @ApiParam(value = "이름", required = true)
    @Pattern(regexp = "^[가-힣]{1,8}|[a-zA-Z]{2,8}$")
    @NotEmpty
    private String name;

    @ApiModelProperty(name = "nickname", example = "별명")
    @ApiParam(value = "닉네임", required = true)
    @Pattern(regexp = "^[0-9a-zA-Z가-힣]{4,20}$")
    @Length(min = 2, max=10)
    @NotEmpty
    private String nickname;

    @ApiModelProperty(name = "city", example = "부산")
    @ApiParam(value = "도시", required = true)
    @Enum(enumClass = City.class, ignoreCase = true)
    private String city;

    @ApiModelProperty(name = "position", example = "개발자")
    @ApiParam(value = "포지션", required = true)
    @NotEmpty
    private String position;

    @ApiModelProperty(name = "dpositionList", example = "[\"frontend\",\"devops\"]")
    @ApiParam(value = "세부 포지션", required = false)
    private List<String> dpositionList;

    @ApiModelProperty(name = "techList", example = "[{\"python\":\"중\"}, {\"java\":\"상\"}]")
    @ApiParam(value = "기술스택 리스트", required = false)
    private List<HashMap<String,String>> techList;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .create_date(LocalDateTime.now())
                .name(name)
                .nickname(nickname)
                .city(city)
                .banned(Boolean.FALSE)
                .position(position)
                .is_active(Boolean.TRUE)
                .authority(Authority.ROLE_USER)
                .build();
    }

}