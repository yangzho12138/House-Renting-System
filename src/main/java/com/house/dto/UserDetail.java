package com.house.dto;

import com.house.enums.UserTypeEnum;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * @version 用户认证信息
 * @since 2022/5/20
 **/
public class UserDetail {
    private Integer id;

    private String username;

    private String password;

    private Boolean enabled;

    private List<GrantedAuthority> authorities;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
