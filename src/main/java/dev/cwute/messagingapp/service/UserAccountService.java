package dev.cwute.messagingapp.service;

import dev.cwute.messagingapp.entity.UserAccount;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface UserAccountService {
  long register(UserAccount userAccount);

  List<String> getUsers(HttpServletRequest httpServletRequest);

  void login(UserAccount userAccount);
}
