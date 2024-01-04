package com.bit.lot.flower.auth.store.handler;


import com.bit.lot.flower.auth.common.valueobject.AuthId;
import com.bit.lot.flower.auth.store.dto.StoreMangerSignUpCommand;
import com.bit.lot.flower.auth.store.mapper.StoreManagerMessageMapper;
import com.bit.lot.flower.auth.store.message.StoreMangerCreateRequest;
import com.bit.lot.flower.auth.store.service.StoreManagerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StoreMangerCreateHandler {

  private final StoreMangerCreateRequest storeMangerCreateRequest;
  private final StoreManagerService<AuthId> storeManagerService;


  @Transactional
  public void createStoreManager(StoreMangerSignUpCommand dto) throws JsonProcessingException {
    Long signUpId = storeManagerService.signUp(dto);
    storeMangerCreateRequest.publish(
        StoreManagerMessageMapper.createStoreManagerCommandWithPk(dto, signUpId));
  }


}
