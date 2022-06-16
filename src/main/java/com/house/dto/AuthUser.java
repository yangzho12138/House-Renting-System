package com.house.dto;

import com.house.enums.UserStatusEnum;
import com.house.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @version 用户认证信息
 * @since 2022/5/20
 **/
public class AuthUser implements UserDetails {

    private static final long serialVersionUID = 29660289480674692L;

    private User user;

    private List<? extends GrantedAuthority> authorities;

    public AuthUser(User user, List<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public String getPassword() {
        return user.getPassword();
    }

    public boolean isEnabled() {
        return UserStatusEnum.ENABLE.getCode().equals(user.getStatus());
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public String getPhone() {
        return user.getPhone();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
