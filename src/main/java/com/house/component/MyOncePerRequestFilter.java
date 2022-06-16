package com.house.component;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONString;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.house.common.Constant;
import com.house.common.Result;
import com.house.dto.AuthUser;
import com.house.pojo.User;
import com.house.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

/**
 * @version 认证过滤器
 * @since 2022/6/7
 */
@Component
public class MyOncePerRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(MyOncePerRequestFilter.class);

    @Value("${jwt.header}")
    private String header;

    private final RedisCache redisCache;

    public MyOncePerRequestFilter(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        // header的值是在yml文件中定义的 “Authorization”
        String token = request.getHeader(header);
        logger.info("MyOncePerRequestFilter-token = " + token);
        if (!StrUtil.isEmpty(token)) {
            String phone = null;
            try {
                Claims claims = JwtUtil.parseJWT(token);
                phone = claims.getSubject();
            } catch (Exception e) {
                e.printStackTrace();
                WriteJSON(request,response,Result.error("非法Token，请重新登陆"));
                return;
            }
            String redisToken = redisCache.getCacheObject(Constant.REDIS_TOKEN_PREFIX + phone);
            logger.info("MyOncePerRequestFilter-redisToken = " + redisToken);
            if (StrUtil.isEmpty(redisToken)) {
                //输出JSON
                WriteJSON(request,response,Result.error("Token 令牌验证失败，已经过期"));
                return;
            }

            //对比前端发送请求携带的的token是否与redis中存储的一致
            if (redisToken.equals(token)) {
                JSONObject authUserJson = redisCache.getCacheObject(Constant.REDIS_USER_INFO_PREFIX + phone);
                User user = authUserJson.getObject("user", User.class);
                List<SimpleGrantedAuthority> authorities = authUserJson.getObject("authorities", new TypeReference<List<SimpleGrantedAuthority>>(){});
                if (Objects.isNull(user) || Objects.isNull(authorities)){
                    WriteJSON(request,response,Result.error("用户未登录"));
                    return;
                }
                AuthUser authUser = new AuthUser(user, authorities);

                logger.info("MyOncePerRequestFilter-authUser = " + authUser);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
    private void WriteJSON(HttpServletRequest request,
                           HttpServletResponse response,
                           Object obj) throws IOException, ServletException {
        //这里很重要，否则页面获取不到正常的JSON数据集
        response.setContentType("application/json;charset=UTF-8");

        //跨域设置
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Method", "POST,GET,PUT,DELETE");
        //输出JSON
        PrintWriter out = response.getWriter();
        out.write(JSON.toJSONString(obj));
        out.flush();
        out.close();
    }
}


