package com.bit.lot.flower.auth.common.security;

import com.bit.lot.flower.auth.common.util.CookieUtil;
import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.util.RedisRefreshTokenUtil;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import java.time.Duration;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class IssueRefreshRefreshTokenInCookie implements RefreshTokenStrategy {

  @Value("${cookie.refresh.http.domain}")
  private String domain;
  @Value("${cookie.refresh.token.name}")
  private String refreshCookieName;

  private final RedisRefreshTokenUtil redisRefreshTokenUtil;

  @Override
  public void createRefreshToken(String userId, HttpServletResponse response) {
    String refreshToken = JwtUtil.generateRefreshToken(String.valueOf(userId));
    redisRefreshTokenUtil.saveRefreshToken(userId, refreshToken,
        Long.parseLong(SecurityPolicyStaticValue.REFRESH_EXPIRATION_TIME));
    response.setHeader(refreshCookieName,
        CookieUtil.createRefreshNoCORSCookie(refreshCookieName, refreshToken,
        Duration.ofDays(1), domain).toString());
  }

  @Override
  public void invalidateRefreshToken(String id, HttpServletResponse response) {
    CookieUtil.deleteCookie(response,refreshCookieName, domain);
    redisRefreshTokenUtil.deleteRefreshToken(id);
  }


}
