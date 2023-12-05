package com.example.mangoplace.signup.security;

import com.example.mangoplace.signup.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils { //jwt 토큰을 생성하고 유효성을 검사
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.token-validity-in-seconds}")
    private long jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        //사용자 정보를 기반으로 jwt토큰 생성

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    //Header에서 JWT 추출
    public String getJwt() {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                        .getRequest();

        // 프론트에서 헤더에 담아 넘겨주는 이름
        String authorizationHeader = request.getHeader("Authorization");

        // 만약 Authorization 헤더가 존재하고 "Bearer "로 시작한다면 해당 부분을 제거하고 반환
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // "Bearer " 부분을 제외한 토큰 반환
        }

        return authorizationHeader;
    }


    //TODO: 여기 수정해야함
    public String getUsernameFromToken() throws Exception{
        String access = getJwt();
        logger.info(access);

        if (access == null || access.length() == 0){
            throw new Exception("토큰이 비어있습니다.");
        }
        Jws<Claims> claims;

        try{
            claims = Jwts.parser()
                    .setSigningKey(key())
                    .parseClaimsJws(access);
        } catch (Exception ignored) {

            throw new Exception("유효하지 않은 토큰입니다.");
        }
        return claims.getBody().get("userName", String.class);
    }

    public boolean validateJwtToken(String authToken) {
        //주어진 토큰이 유효한지 검사, 만료, 잘못된 토큰 및 예외 처리
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new IllegalArgumentException("Security Context에 인증정보가 없습니다.");
        }

        return Long.parseLong(authentication.getName());
    }
}