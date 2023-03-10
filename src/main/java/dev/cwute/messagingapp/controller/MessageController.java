package dev.cwute.messagingapp.controller;

import dev.cwute.messagingapp.entity.message.MessageDto;
import dev.cwute.messagingapp.entity.message.MessageView;
import dev.cwute.messagingapp.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
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
  public void sendMessage(@RequestBody MessageDto messageDto, HttpServletRequest httpServletRequest) {
    messageService.send(messageDto, httpServletRequest);
  }

  @GetMapping("/received")
  public ResponseEntity<List<MessageView>> getReceivedMessages(
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.of(
        Optional.ofNullable(messageService.getReceivedMessagesForUser(httpServletRequest)));
  }

  @DeleteMapping("/received/{id}")
  public ResponseEntity deleteReceivedMessage(
      HttpServletRequest httpServletRequest, @PathVariable long id) {
    messageService.removeReceivedMessage(httpServletRequest, id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/sent")
  public ResponseEntity<List<MessageView>> getSentMessages(HttpServletRequest httpServletRequest) {
    return ResponseEntity.of(
        Optional.ofNullable(messageService.getSentMessagesForUser(httpServletRequest)));
  }

  @DeleteMapping("/sent/{id}")
  public ResponseEntity deleteSentMessage(
      HttpServletRequest httpServletRequest, @PathVariable long id) {
    messageService.deleteSentMessage(httpServletRequest, id);
    return ResponseEntity.noContent().build();
  }
}
