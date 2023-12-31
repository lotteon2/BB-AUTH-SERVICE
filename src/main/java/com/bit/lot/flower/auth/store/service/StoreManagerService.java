package com.bit.lot.flower.auth.store.service;

import com.bit.lot.flower.auth.common.valueobject.AuthId;
import com.bit.lot.flower.auth.store.dto.StoreMangerSignUpCommand;
import org.springframework.stereotype.Service;

@Service
public interface StoreManagerService<ID extends AuthId> {
  Long signUp(StoreMangerSignUpCommand signUpDto);
  void logout(ID id);
}
