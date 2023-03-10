package dev.cwute.messagingapp.controller;

import dev.cwute.messagingapp.entity.UserAccount;
import dev.cwute.messagingapp.service.UserAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserAccountController {

  private final UserAccountService userAccountService;

  @PostMapping("/register")
  public ResponseEntity register(@RequestBody UserAccount userAccount) throws URISyntaxException {
    var registered = userAccountService.register(userAccount);
    return ResponseEntity.created(new URI("/api/v1/users/" + registered)).build();
  }

  @PostMapping("/login")
  public ResponseEntity login(@RequestBody UserAccount userAccount) {
    var loggedIn = userAccountService.checkCredentials(userAccount);
    return ResponseEntity.ok(loggedIn);
  }
}
