package dev.cwute.messagingapp.service;

import dev.cwute.messagingapp.entity.UserAccount;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/** Service for user account operations. */
public interface UserAccountService {
  long register(UserAccount userAccount);

  List<String> getUsers(HttpServletRequest httpServletRequest);

  void login(UserAccount userAccount);
}
