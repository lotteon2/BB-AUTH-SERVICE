package com.bit.lot.flower.auth.social.http.filter;

import com.bit.lot.flower.auth.common.EncryptionUtil;
import com.bit.lot.flower.auth.common.security.SystemAuthenticationSuccessHandler;
import com.bit.lot.flower.auth.common.valueobject.AuthId;
import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.social.dto.command.EncryptedSocialLoginRequestCommand;
import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;
import com.bit.lot.flower.auth.social.exception.SocialAuthException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.util.UriUtils;

public class SocialAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final SystemAuthenticationSuccessHandler handler;
  private final AuthenticationManager socialAuthenticationManager;

  @Autowired
  public SocialAuthenticationFilter(
      SystemAuthenticationSuccessHandler handler,
      AuthenticationManager socialAuthenticationManager) {
    super(socialAuthenticationManager);
    this.handler = handler;
    this.socialAuthenticationManager = socialAuthenticationManager;
  }


  private Authentication getAuthenticationFromCommand(SocialLoginRequestCommand command) {
    return new UsernamePasswordAuthenticationToken(command.getSocialId(), null,
        Collections.singleton(new SimpleGrantedAuthority(Role.ROLE_SOCIAL_USER.name())));
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      EncryptedSocialLoginRequestCommand encryptedCommand = mapper.readValue(
          request.getInputStream(),
          EncryptedSocialLoginRequestCommand.class);
      SocialLoginRequestCommand decryptedCommand = decryptCommand(encryptedCommand);
      Authentication authentication = getAuthenticationFromCommand(decryptedCommand);
      socialAuthenticationManager.authenticate(authentication);
      request.setAttribute("command", decryptedCommand);

      return authentication;

    } catch (SocialAuthException | IOException e) {
      throw new SocialAuthException(e.getMessage());
    }
  }

  @Override
  public void successfulAuthentication(HttpServletRequest request, HttpServletResponse
      response, FilterChain
      chain,
      Authentication authResult) throws IOException, ServletException {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(authResult);
    SecurityContextHolder.setContext(context);
    handler.onAuthenticationSuccess(request, response, chain, authResult);
  }

  private SocialLoginRequestCommand decryptCommand(
      EncryptedSocialLoginRequestCommand encryptedCommand) {
    return SocialLoginRequestCommand.builder()
        .nickname(UriUtils.decode(encryptedCommand.getNickname(), StandardCharsets.UTF_8))
        .email(EncryptionUtil.decrypt(encryptedCommand.getEmail()))
        .socialId(new AuthId(Long.valueOf(EncryptionUtil.decrypt(encryptedCommand.getSocialId()))))
        .phoneNumber(EncryptionUtil.decrypt(encryptedCommand.getPhoneNumber())).build();
  }



}