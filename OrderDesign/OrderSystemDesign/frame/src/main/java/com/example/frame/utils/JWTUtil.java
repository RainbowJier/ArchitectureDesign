package com.example.frame.utils;

import com.example.frame.enums.BizCodeEnum;
import com.example.frame.exception.BizException;
import com.example.frame.model.entity.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @Author: RainbowJier
 * @Description: 👺🐉😎
 * @Date: 2024/10/5 19:59
 * @Version: 1.0
 */

@Slf4j
public class JWTUtil {

    private static final String SUBJECT = "Frank";

    private static final String SECRET = "Frank";

    private static final String TOKEN_PREFIX = "James";

    private static final long EXPIRED = 1000 * 60 * 60 * 24 * 7;

    /**
     * generate token.
     */
    public static String generateJsonToken(LoginUser loginUser) {
        if (loginUser == null) {
            throw new NullPointerException("用户不存在");
        }

        String token = Jwts.builder()
                .setSubject(SUBJECT)
                .claim("head_img", loginUser.getHeadImg() == null? "" : loginUser.getHeadImg())
                .claim("account_no", loginUser.getAccountNo())
                .claim("username", loginUser.getUsername())
                .claim("mail", loginUser.getMail() == null? "" : loginUser.getMail())
                .claim("phone", loginUser.getPhone())
                .claim("auth", loginUser.getAuth())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRED))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        token = TOKEN_PREFIX + token;
        return token;
    }

    /**
     * 校验 token 的合法性
     */
    public static Claims checkJWT(String token) {
        try{
            return Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
        }catch (Exception e){
            log.error("token 校验失败:{}",e.getMessage());
            throw new BizException(BizCodeEnum.TOKEN_INVALID);
        }
    }


}
