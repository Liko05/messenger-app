package dev.cwute.messagingapp.service.impl;

import dev.cwute.messagingapp.entity.UserAccount;
import dev.cwute.messagingapp.repository.UserAccountRepository;
import dev.cwute.messagingapp.service.UserAccountService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/** Service for user account operations. */
@Service
@Slf4j
public class UserAccountServiceImpl extends UserSecurityBase implements UserAccountService {
  public UserAccountServiceImpl(
      PasswordEncoder passwordEncoder, UserAccountRepository userAccountRepository) {
    super(userAccountRepository, passwordEncoder);
  }

  /**
   * Registers a new user account.
   *
   * @param userAccount the user account to register
   * @return the id of the newly created user account
   */
  @Override
  public long register(UserAccount userAccount) {
    log.info("Registering user: {}", userAccount.getUsername());
    userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
    var savedUser = userAccountRepository.save(userAccount);
    log.info("User registered: {}", savedUser);

    return savedUser.getId();
  }

  /**
   * Gets a list of all user accounts.
   *
   * @param httpServletRequest the request
   * @return a list of all user accounts
   */
  @Override
  public List<String> getUsers(HttpServletRequest httpServletRequest) {
    checkCredentials(httpServletRequest);
    log.info("Retrieving all usernames");
    var users = userAccountRepository.findAll();
    return users.stream().map(UserAccount::getUsername).toList();
  }

  /**
   * Logs in a user.
   *
   * @param userAccount
   */
  @Override
  public void login(UserAccount userAccount) {
    checkCredentials(userAccount);
  }
}
