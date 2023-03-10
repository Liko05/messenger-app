package dev.cwute.messagingapp.controller;

import dev.cwute.messagingapp.entity.UserAccount;
import dev.cwute.messagingapp.service.UserAccountService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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
    userAccountService.login(userAccount);
    return ResponseEntity.ok().build();
  }

  @GetMapping("")
  public ResponseEntity<List<String>> getUsers(HttpServletRequest httpServletRequest) {
    return ResponseEntity.of(Optional.ofNullable(userAccountService.getUsers(httpServletRequest)));
  }
}
