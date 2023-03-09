package dev.cwute.messagingapp.service;

import dev.cwute.messagingapp.entity.UserAccount;

public interface UserAccountService {
    boolean checkCredentials(UserAccount userAccount);
    long register(UserAccount userAccount);
}
