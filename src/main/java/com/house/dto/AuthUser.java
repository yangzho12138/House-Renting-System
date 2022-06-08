package com.house.dto;

import com.house.enums.UserStatusEnum;
import com.house.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @version 用户认证信息
 * @since 2022/5/20
 **/
public class AuthUser implements UserDetails {

    private static final long serialVersionUID = 29660289480674692L;

    private User user;

    private Collection<? extends GrantedAuthority> authorities;

    public AuthUser(User user, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
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

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
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
