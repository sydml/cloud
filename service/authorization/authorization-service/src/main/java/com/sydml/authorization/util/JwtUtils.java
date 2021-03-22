package com.sydml.authorization.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.sql.Date;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyuming
 * @date 2021-03-04 20:35:41
 */
public class JwtUtils {
    //SING签名的设置，不能对外暴露
    public static final String SING = "token!J1JK3JH^&g%f*f@f*(f!)fs*#s*$H3J4DK43";

    public static String getToken(Map<String, String> map, Long expireSeconds) {

        //设置令牌的过期时间

        //创建JWT builder
        JWTCreator.Builder builder = JWT.create();

        //payload
        map.forEach(builder::withClaim);
        return builder.withExpiresAt(Date.from(Instant.now().plusSeconds(expireSeconds))).sign(Algorithm.HMAC256(SING));
    }

    public static DecodedJWT parseToken(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
        return verify;
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("username", "jack");
        map.put("admin", "true");
        String token = getToken(map, 1000L);
        System.out.println(token);
        DecodedJWT decodedJWT = parseToken(token);

    }
}
