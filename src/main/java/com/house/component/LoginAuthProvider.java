package com.house.component;

import com.house.service.impl.AuthUserDetailsServiceImpl;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
/**
 * 登录处理，
 */
@Component
public class LoginAuthProvider implements AuthenticationProvider {

    private final BCryptPasswordEncoderUtil bCryptPasswordEncoderUtil;

    private final AuthUserDetailsServiceImpl authUserDetailsService;

    public LoginAuthProvider(BCryptPasswordEncoderUtil bCryptPasswordEncoderUtil, AuthUserDetailsServiceImpl authUserDetailsService) {
        this.bCryptPasswordEncoderUtil = bCryptPasswordEncoderUtil;
        this.authUserDetailsService = authUserDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        //获取用户名和密码
        String username = (String) auth.getPrincipal();
        String password = (String) auth.getCredentials();
        UserDetails userDetail  = authUserDetailsService.loadUserByUsername(username);
        if (!userDetail.isEnabled()){
            throw new DisabledException("该账号已禁用，请联系管理员");
        }else if (!userDetail.isAccountNonExpired()){
            throw new AccountExpiredException("该账号已过期,请联系管理员");
        }else if(!userDetail.isAccountNonLocked()){
            throw new LockedException("该账号已被锁定，请联系管理员");
        }else if(!userDetail.isCredentialsNonExpired()){
            throw new CredentialsExpiredException("该账号的登录凭证已过期，请重新登录");
        }
        if(!bCryptPasswordEncoderUtil.matches(password,userDetail.getPassword())){
            throw  new BadCredentialsException("密码错误请重新输入");
        }
        return new UsernamePasswordAuthenticationToken(userDetail, password, userDetail.getAuthorities());
    }

    // supports函数用来指明该Provider是否适用于该类型的认证，如果不合适，则寻找另一个Provider进行验证处理。
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
