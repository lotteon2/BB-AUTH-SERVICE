package com.bit.lot.flower.auth.social.service;

import com.bit.lot.flower.auth.social.exception.SocialAuthException;
import com.bit.lot.flower.auth.social.entity.SocialAuth;
import com.bit.lot.flower.auth.social.repository.SocialAuthJpaRepository;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class canSignUpAfter24H implements
    SocialAuthResignUpStrategy {

  private final long RESIGN_UP_POSSIBLE_TIME_AFTER_WITHDRAWAL =24 ;
  private final SocialAuthJpaRepository repository;
  @Override
  public void resignUp(SocialAuth socialAuth) {
    if (Boolean.TRUE.equals(socialAuth.getIsDeleted())) {
      if (socialAuth.getLastLogoutTime().plusHours(RESIGN_UP_POSSIBLE_TIME_AFTER_WITHDRAWAL)
          .isAfter(
              ZonedDateTime.now())) {
        throw new SocialAuthException("24시간 이내에는 재 회원가입을 할 수 없습니다.");
      } else {
        SocialAuth updatedSocialAuth = updatedResignUpSocialAuth(socialAuth);
        updatedSocialAuth.setIsDeleted(false);
        repository.save(updatedSocialAuth);
      }
    }
  }

  private SocialAuth updatedResignUpSocialAuth(SocialAuth socialAuth) {
    return SocialAuth.builder().lastLogoutTime(null)
        .oauthId(socialAuth.getOauthId()).build();
  }
}
