package dev.cwute.messagingapp.service.impl;

import dev.cwute.messagingapp.entity.UserAccount;
import dev.cwute.messagingapp.exception.UnauthorizedUser;
import dev.cwute.messagingapp.exception.UserNotFound;
import dev.cwute.messagingapp.repository.UserAccountRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public abstract class UserSecurityBase {
  protected final UserAccountRepository userAccountRepository;
  protected final PasswordEncoder passwordEncoder;

  public UserSecurityBase(
      UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder) {
    this.userAccountRepository = userAccountRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public String checkCredentials(HttpServletRequest httpServletRequest) {
    var header =
        httpServletRequest.getHeader("Credentials");

    if(header == null){
        throw new UnauthorizedUser("No credentials provided");
    }

    var credentials = header.split(":");

    if (credentials.length != 2) {
      throw new UnauthorizedUser("Missing header");
    }

    var userAccount =
        new UserAccount().builder().password(credentials[1]).username(credentials[0]).build();

    log.info("Checking credentials for user: {}", userAccount.getUsername());

    var userAccountFromDb =
        userAccountRepository
            .findByUsername(userAccount.getUsername())
            .orElseThrow(() -> new UserNotFound("User not found"));
    if (userAccountFromDb != null
        && passwordEncoder.matches(userAccount.getPassword(), userAccountFromDb.getPassword())) {
      return userAccount.getUsername();
    }
    throw new UnauthorizedUser("Unauthorized user");
  }

  public String checkCredentials(UserAccount userAccount) {
    log.info("Checking credentials for user: {}", userAccount.getUsername());

    var userAccountFromDb =
        userAccountRepository
            .findByUsername(userAccount.getUsername())
            .orElseThrow(() -> new UserNotFound("User not found"));

    if (userAccountFromDb != null
        && passwordEncoder.matches(userAccount.getPassword(), userAccountFromDb.getPassword())) {
      return userAccount.getUsername();
    }

    throw new UnauthorizedUser("Unauthorized user");
  }
}
