package com.bit.lot.flower.auth.social.message;

import com.bit.lot.flower.auth.social.dto.response.UserFeignLoginResponse;
import com.bit.lot.flower.auth.social.dto.message.SocialUserLoginDto;
import com.bit.lot.flower.auth.social.http.valueobject.UserId;
import org.springframework.stereotype.Component;


@Component
public interface LoginSocialUserEventPublisher {
  public UserFeignLoginResponse publish(SocialUserLoginDto dto);
}
