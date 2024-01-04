package com.bit.lot.flower.auth.store.message;

import com.bit.lot.flower.auth.common.valueobject.AuthId;
import com.bit.lot.flower.auth.store.dto.CreateStoreMangerCommand;
import com.bit.lot.flower.auth.store.dto.StoreMangerSignUpCommand;
import com.bit.lot.flower.auth.store.http.feign.CreateStoreManagerFeignRequest;
import com.bit.lot.flower.auth.store.mapper.StoreManagerMessageMapper;
import com.bit.lot.flower.auth.store.service.StoreManagerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class StoreMangerCreateRequestHandler implements
    StoreMangerCreateRequest {

  private final StoreManagerService<AuthId> storeManagerService;
  private final CreateStoreManagerSqsPublisher sqsPublisher;
  private final CreateStoreManagerFeignRequest feignRequest;

  @Transactional
  @Override
  public void publish(StoreMangerSignUpCommand dto) throws JsonProcessingException {
    Long signUpId = storeManagerService.signUp(dto);
    feignRequest.create(StoreManagerMessageMapper.createStoreManagerCommandWithPk(dto, signUpId));
    sqsPublisher.publish();

  }
}
