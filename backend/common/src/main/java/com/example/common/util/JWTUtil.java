package com.example.common.util;

import com.example.common.entity.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

/**
 * @Author: RainbowJier
 * @Description: 👺🐉😎
 * @Date: 2024/10/5 19:59
 * @Version: 1.0
 */

@Slf4j
public class JWTUtil {

    private static final String SUBJECT = "frank";

    private static final String SECRET = "SECRET_frank";

    private static final long EXPIRED = 1000 * 60 * 60 * 24 * 7;

    /**
     * generate token.
     */
    public static String generateJsonToken(LoginUser loginUser) {
        if (loginUser == null) {
            throw new NullPointerException("User is not exist.");
        }

        String token = Jwts.builder()
                .setSubject(SUBJECT)
                .claim("account", loginUser.getAccount())
                .claim("username", loginUser.getUsername())
                .claim("email", loginUser.getEmail() == null? "" : loginUser.getEmail())
                .claim("phone", loginUser.getPhone())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRED))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        return token;
    }


    /**
     * Check validity of token.
     */
    public static Claims checkJWT(String token) {
        try{
            return Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            log.error("jwt verify error:{}",e.getMessage());
            return null;
        }
    }


}
