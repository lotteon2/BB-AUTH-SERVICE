package com.bit.lot.flower.auth.social.handler;

import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;
import com.bit.lot.flower.auth.social.dto.response.UserFeignLoginResponse;
import com.bit.lot.flower.auth.social.message.LoginSocialUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SocialDataHandler {
/*
Auth User is created at the Filter. so can't combined with
 */
  private final LoginSocialUserRequest userDataRequest;

  @Transactional
  public UserFeignLoginResponse loginRequestWithOuternalService(SocialLoginRequestCommand command) {
    UserFeignLoginResponse userFeignLoginResponse = userDataRequest.request(
        command);
    return userFeignLoginResponse;
  }
}
