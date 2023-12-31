package com.bit.lot.flower.auth.social.service;

import com.bit.lot.flower.auth.social.entity.SocialAuth;
import com.bit.lot.flower.auth.common.valueobject.AuthId;
import org.springframework.stereotype.Service;

@Service
public interface SignUpStrategy<ID extends AuthId> {

  public SocialAuth signUp(ID socialId);

}
