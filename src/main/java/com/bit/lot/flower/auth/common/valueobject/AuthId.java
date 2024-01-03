package com.bit.lot.flower.auth.common.valueobject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@NoArgsConstructor
@SuperBuilder
@Getter
public class AuthId extends BaseId<Long> {
  public AuthId(String value){
    super(Long.valueOf(value));
  }
  public AuthId(Long value) {
    super(value);
  }

}
