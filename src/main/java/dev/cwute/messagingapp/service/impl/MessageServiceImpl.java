package dev.cwute.messagingapp.service.impl;

import dev.cwute.messagingapp.entity.message.Message;
import dev.cwute.messagingapp.entity.message.MessageDto;
import dev.cwute.messagingapp.entity.message.MessageView;
import dev.cwute.messagingapp.entity.UserAccount;
import dev.cwute.messagingapp.exception.UnauthorizedUser;
import dev.cwute.messagingapp.exception.UserNotFound;
import dev.cwute.messagingapp.repository.MessageRepository;
import dev.cwute.messagingapp.repository.UserAccountRepository;
import dev.cwute.messagingapp.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MessageServiceImpl extends UserSecurityBase implements MessageService {

  private final MessageRepository messageRepository;

  public MessageServiceImpl(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder, MessageRepository messageRepository) {
    super(userAccountRepository, passwordEncoder);
    this.messageRepository = messageRepository;
  }

  @Override
  public long send(MessageDto messageDto) {
    var message = messageDto.toMessage();
    var sender = userAccountRepository.findByUsername(messageDto.getSender());
    var recipients = userAccountRepository.findAllByUsernameIn(messageDto.getRecipients());

    message.setSender(sender.orElseThrow(() -> new UserNotFound("Sender not found")));
    List<UserAccount> recipientList = (List<UserAccount>) recipients;
    message.setRecipients(recipientList);

    var savedMessage = messageRepository.save(message);
    return savedMessage.getId();
  }

  @Override
  public List<MessageView> getReceivedMessagesForUser(HttpServletRequest httpServletRequest) {
    var username = checkCredentials(httpServletRequest);
    var user =
        userAccountRepository
            .findByUsername(username)
            .orElseThrow(() -> new UserNotFound("User not found"));
    return user.getReceivedMessages().stream().map(Message::toMessageView).toList();
  }

  @Override
  public List<MessageView> getSentMessagesForUser(HttpServletRequest httpServletRequest) {
    var username = checkCredentials(httpServletRequest);
    var user =
        userAccountRepository
            .findByUsername(username)
            .orElseThrow(() -> new UserNotFound("User not found"));
    return user.getSentMessages().stream().map(Message::toMessageView).toList();
  }

  @Override
  public void removeReceivedMessage(HttpServletRequest httpServletRequest, long messageId) {
    var username = checkCredentials(httpServletRequest);
    log.info("Trying to remove message: {} for user: {}", messageId, username);
    userAccountRepository
        .findByUsername(username)
        .orElseThrow(() -> new UserNotFound("User not found"));
    var message =
        messageRepository.findById(messageId).orElseThrow(() -> new RuntimeException("Not found"));

    var recipients = message.getRecipients();
    recipients.removeIf(userAccount -> userAccount.getUsername().equals(username));

    message.setRecipients(recipients);
    messageRepository.save(message);
  }

  @Override
  public void deleteSentMessage(HttpServletRequest httpServletRequest, long messageId) {
    var username = checkCredentials(httpServletRequest);
    log.info("Trying to remove sent message: {} for user: {}", messageId, username);
    userAccountRepository
        .findByUsername(username)
        .orElseThrow(() -> new UserNotFound("User not found"));
    var message =
        messageRepository.findById(messageId).orElseThrow(() -> new RuntimeException("Not found"));

    if (!message.getSender().getUsername().equals(username)) {
      throw new UnauthorizedUser("You are not the sender of this message.");
    }

    message.setSender(null);
    messageRepository.save(message);
  }
}
