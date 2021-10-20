package com.ssafy.match.member.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails, Serializable {
    private static final long serialVersionUID = 174726374856727L;

    private String id;
    private String password;
    private String email;
    private boolean is_active;
    private boolean banned;
    private String nickname;
    private Collection<GrantedAuthority> authorities;	//권한 목록


    /**
     * 해당 유저의 권한 목록
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * 비밀번호
     */
    @Override
    public String getPassword() {
        return password;
    }


    /**
     * PK값
     */
    @Override
    public String getUsername() {
        return id;
    }

    /**
     * 계정 만료 여부
     * true : 만료 안됨
     * false : 만료
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정 잠김 여부
     * true : 잠기지 않음
     * false : 잠김
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return !banned;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    /**
     * 사용자 활성화 여부
     * ture : 활성화
     * false : 비활성화
     * @return
     */
    @Override
    public boolean isEnabled() {
        //이메일이 인증되어 있고 계정이 잠겨있지 않으면 true
        return (is_active && !banned);
    }
}
