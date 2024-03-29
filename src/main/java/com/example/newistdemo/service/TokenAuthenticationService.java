package com.example.newistdemo.service;

import com.example.newistdemo.constant.ConstantKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class TokenAuthenticationService {
    static final long EXPIRATIONTIME = 432_000_000;     // 5天
    static final String SECRET = "P@ssw02d";            // JWT密码
    static final String TOKEN_PREFIX = "Bearer";        // Token前缀
    static final String HEADER_STRING = "Authorization";// 存放Token的Header Key

    // JWT生成方法
    public static void addAuthentication(HttpServletResponse response, String username, List<GrantedAuthority> authorities) {
        String authority ="";
          for (int i=0;i<authorities.size();i++){
              authority= authorities.get(i).getAuthority()+","+authority;
          }

        // 生成JWT
        String JWT = Jwts.builder()
                // 保存权限（角色）
                .claim("authorities", authority)
                // 用户名写入标题
                .setSubject(username)
                // 有效期设置
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                // 签名设置
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        response.addHeader("Authorization", "Bearer " + JWT);
//        // 将 JWT 写入 body
//        try {
//            response.setContentType("application/json");
//            response.setStatus(HttpServletResponse.SC_OK);
//            response.getOutputStream().println(JSONResult.fillResultString(0, "", JWT));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    // JWT验证方法
    public static Authentication getAuthentication(HttpServletRequest request) {
        // 从Header中拿到token
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            // 解析 Token，解析不了加个异常捕获，处理失败信息
            Claims claims = Jwts.parser()
                    // 验签
                    .setSigningKey(SECRET)
                    // 去掉 Bearer
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();


            // 拿用户名
            String user = claims.getSubject();
            String authorities1 = (String) claims.get("authorities");
            authorities1="";
            // 得到 权限（角色）
           List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("authorities"));
            //List<GrantedAuthority> authorities = (List<GrantedAuthority>) claims.get("authorities");

            // 返回验证令牌
            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null, authorities) :
                    null;
        }
        return null;
    }
}
