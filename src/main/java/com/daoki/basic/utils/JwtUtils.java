package com.daoki.basic.utils;

import com.daoki.basic.VO.response.user.UserVO;
import com.daoki.basic.service.IUserService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    @Autowired
    private IUserService userService;
    /**
     * 过期时间5分钟
     */
    private static final long EXPIRE_TIME = 5 * 60 * 1000;
    /**
     * 加密密钥
     */
    @Value("${login.key}")
    private String KEY = "daoki_backend";

    /**
     * 生成token
     *
     * @param id       用户id
     * @param userName 用户名
     * @return
     */
    public String createToken(String id, String userName) {
        Map<String, Object> header = new HashMap();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        //setID:用户ID
        //setExpiration:token过期时间  当前时间+有效时间
        //setSubject:用户名
        //setIssuedAt:token创建时间
        //signWith:加密方式
        JwtBuilder builder = Jwts.builder().setHeader(header)
                .setId(id)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .setSubject(userName)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, KEY);
        return builder.compact();
    }

    /**
     * 验证token是否有效
     *
     * @param token 请求头中携带的token
     * @return token验证结果  2-token过期；1-token认证通过；0-token认证失败
     */
    public UserVO verify(String token) {
        Claims claims = null;
        try {
            //token过期后，会抛出ExpiredJwtException 异常，通过这个来判定token过期，
            claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return null;
        }
        //从token中获取用户id，查询该Id的用户是否存在，存在则token验证通过
        Long id = Long.parseLong(claims.getId());
        return userService.getUserById(id);
    }
}
