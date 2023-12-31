package com.bit.lot.flower.auth.social.service;

import com.bit.lot.flower.auth.common.valueobject.BaseId;
import org.springframework.stereotype.Service;

@Service
public interface SocialLogoutStrategy<ID extends BaseId> {
  public void logout(ID socialId);
}
