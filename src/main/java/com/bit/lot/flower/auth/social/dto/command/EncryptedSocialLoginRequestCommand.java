package com.bit.lot.flower.auth.social.dto.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EncryptedSocialLoginRequestCommand {

  @NotNull
  private String socialId;
  @NotNull
  private String email;
  @NotNull
  private String phoneNumber;
  @NotNull
  private String nickname;

}
