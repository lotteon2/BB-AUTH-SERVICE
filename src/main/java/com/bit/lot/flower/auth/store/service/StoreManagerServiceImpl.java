package com.bit.lot.flower.auth.store.service;


import com.bit.lot.flower.auth.common.valueobject.AuthId;
import com.bit.lot.flower.auth.store.dto.StoreMangerSignUpCommand;
import com.bit.lot.flower.auth.store.entity.StoreManagerAuth;
import com.bit.lot.flower.auth.store.exception.StoreManagerAuthException;
import com.bit.lot.flower.auth.store.mapper.StoreManagerDataMapper;
import com.bit.lot.flower.auth.store.repository.StoreManagerAuthRepository;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.StoreManager;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StoreManagerServiceImpl implements StoreManagerService<AuthId> {

  private final StoreManagerAuthRepository repository;
  private final StoreManagerSingUpService singUpService;

  @Override
  public Long signUp(StoreMangerSignUpCommand signUpDto) {
    return singUpService.signup(signUpDto);
  }

  @Override
  public void logout(AuthId storeManagerId) {
    StoreManagerAuth storeManagerAuth = getStoreManager(storeManagerId);
    repository.save(
        StoreManagerDataMapper.updatedCurrentLogOutTime(storeManagerAuth, ZonedDateTime.now()));
  }



  private StoreManagerAuth getStoreManager(AuthId storeManagerId) {
    return repository.findById(storeManagerId.getValue()).orElseThrow(() -> {
      throw new StoreManagerAuthException("존재하지 않는 스토어 매니저 유저입니다.");
    });
  }
}
