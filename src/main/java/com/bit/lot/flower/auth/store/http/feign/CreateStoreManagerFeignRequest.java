package com.bit.lot.flower.auth.store.http.feign;

import bloomingblooms.response.CommonResponse;
import com.bit.lot.flower.auth.store.dto.CreateStoreMangerCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "create-store-manager-request", url = "${service.user.domain}")
public interface CreateStoreManagerFeignRequest {
  @PostMapping("/client/store-manager")
  CommonResponse<String> create(@RequestBody CreateStoreMangerCommand createStoreMangerCommand);
}
