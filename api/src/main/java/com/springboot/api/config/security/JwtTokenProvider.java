package com.springboot.api.config.security;

import com.springboot.api.config.ConfigProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

  private final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
  private final UserDetailsService userDetailsService;
  private final ConfigProperties.JwtProperties jwtProperties;

  private Key key;
  private final long tokenValidMillisecond = 1000L * 60 * 60;

  @PostConstruct
  protected void init() {
    String seretKeyStr = jwtProperties.getSecret();
    logger.debug("[init] jwtTokenProvider 내 secretKey 초기화 시작");
    seretKeyStr = Base64.getEncoder().encodeToString(seretKeyStr.getBytes(StandardCharsets.UTF_8));
    key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(seretKeyStr));

    logger.debug("[init] jwtTokenProvider 내 secretKey 초기화 완료");
  }

  public String createToken(String userUid, List<String> roles) {
    logger.info("[createToken] 토큰 생성 시작");
    Claims claims = Jwts.claims().setSubject(userUid);
    claims.put("role", roles);
    Date now = new Date();

    String token = Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
        .signWith(key)
        .compact();
    logger.info("[createToken] 토큰 생성 완료");

    return token;
  }

  public Authentication getAuthentication(String token) {
    logger.info("[getAuthentication] 토큰 인증정보 조회 시작");
    UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token));

    logger.info("[getAuthentication] 토큰 인증 정보 조회 완료, UserDatails UserName : {}, getAuthorities: {}",
        userDetails, userDetails.getAuthorities());
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUsername(String token) {
    logger.info("[getUsername] 토큰 기반 회원 구별 정보 추출");
    String info = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
    logger.info("[getUsername] 토큰 기반 회원 구별 정보 추출 완료 info: {}", info);
    return info;
  }

  public String resolveToken(HttpServletRequest request) {
    logger.info("[resolveToken] HTTP 헤더에서 Token 값 추출");
    return request.getHeader("X-Auth-Token");
  }

  public boolean validateToken(String token) {
    logger.info("[validateToken] 토큰 유효 체크 시작");
    try {
      Jws<Claims> claims = Jwts.parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(token);
      logger.info("[validateToken] 토큰 유효시간: {}, now: {}", claims.getBody().getExpiration(),
          new Date());
      return !claims.getBody().getExpiration().before(new Date());
    } catch (Exception e) {
      logger.error("[validateToken] 토큰 유효 예외 발생 \nerror: {}", e.toString());
      return false;
    }
  }


}
