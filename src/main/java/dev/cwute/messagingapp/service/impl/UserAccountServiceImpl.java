package dev.cwute.messagingapp.service.impl;

import dev.cwute.messagingapp.entity.UserAccount;
import dev.cwute.messagingapp.exception.UserNotFound;
import dev.cwute.messagingapp.repository.UserAccountRepository;
import dev.cwute.messagingapp.service.UserAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class UserAccountServiceImpl implements UserAccountService {

  private final UserAccountRepository userAccountRepository;

  private final PasswordEncoder passwordEncoder;

  @Override
  public boolean checkCredentials(UserAccount userAccount) {
    log.info("Checking credentials for user: {}", userAccount.getUsername());
    var userAccountFromDb =
        userAccountRepository
            .findByUsername(userAccount.getUsername())
            .orElseThrow(() -> new UserNotFound("User not found"));
    if (userAccountFromDb != null) {
      return passwordEncoder.matches(userAccount.getPassword(), userAccountFromDb.getPassword());
    }
    return false;
  }

  @Override
  public long register(UserAccount userAccount) {
    log.info("Registering user: {}", userAccount.getUsername());
    userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
    var savedUser = userAccountRepository.save(userAccount);
    log.info("User registered: {}", savedUser);

    return savedUser.getId();
  }
}
