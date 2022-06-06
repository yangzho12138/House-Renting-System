package com.house.utils;

import com.house.pojo.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 令牌处理工具类
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtUtil {

    //盐，秘钥
    private String secret;

    //过期时间的时长
    private Long expiration;

    //令牌头部
    private String header;

    //刷新令牌时间
    private Long refresh_expiration;


    /**
     * 生成JWT
     */
    public String createJWT(String id, String subject, String roles) {
        //当前时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        //构建JwtBuilder
        JwtBuilder builder = Jwts.builder().setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, secret).claim("roles", roles);
        //有过期时间
        if (expiration > 0) {
            builder.setExpiration( new Date( nowMillis + expiration));
        }
        return builder.compact();
    }

    /**
     * 解析JWT
     */
    public Claims parseJWT(String jwtStr){
        return  Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(jwtStr)
                .getBody();
    }

//    public String generateToken(User user, String roles){
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("sub", user.getUsername());
//        claims.put("create", new Date());
//        claims.put("role", roles);
//        return generateToken(claims);
//    }
//
//    public String generateToken(Map<String, Object> claims){
//        Date expirationDate = new Date(System.currentTimeMillis() + expiration);
//        return Jwts.builder().setClaims(claims)
//                .setExpiration(expirationDate)
//                .signWith(SignatureAlgorithm.HS512, secret)
//                .compact();
//    }
//
//    public String generateRefreshToken(User user, String roles){
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("sub", user.getUsername());
//        claims.put()
//    }
}
