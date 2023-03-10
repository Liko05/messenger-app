package dev.cwute.messagingapp.controller;

import dev.cwute.messagingapp.entity.MessageDto;
import dev.cwute.messagingapp.entity.MessageView;
import dev.cwute.messagingapp.service.MessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/messages")
@AllArgsConstructor
@Slf4j
public class MessageController {

  private final MessageService messageService;

  @PostMapping("/send")
  public void sendMessage(@RequestBody MessageDto messageDto) {
    messageService.send(messageDto);
  }

  @GetMapping("/{username}/received")
  public ResponseEntity<List<MessageView>> getReceivedMessages(@PathVariable String username) {
    return ResponseEntity.of(
        Optional.ofNullable(messageService.getReceivedMessagesForUser(username)));
  }

  @DeleteMapping("/{username}/received/{id}")
  public ResponseEntity deleteReceivedMessage(
      @PathVariable String username, @PathVariable long id) {
    messageService.removeReceivedMessage(username, id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{username}/sent")
  public ResponseEntity<List<MessageView>> getSentMessages(@PathVariable String username) {
    return ResponseEntity.of(Optional.ofNullable(messageService.getSentMessagesForUser(username)));
  }

  @DeleteMapping("/{username}/sent/{id}")
  public ResponseEntity deleteSentMessage(@PathVariable String username, @PathVariable long id) {
    messageService.deleteSentMessage(username, id);
    return ResponseEntity.noContent().build();
  }
}
