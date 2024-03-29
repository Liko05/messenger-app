package dev.cwute.messagingapp.service.impl;

import dev.cwute.messagingapp.entity.message.Message;
import dev.cwute.messagingapp.entity.message.MessageDto;
import dev.cwute.messagingapp.entity.message.MessageView;
import dev.cwute.messagingapp.entity.UserAccount;
import dev.cwute.messagingapp.exception.MessageNotFound;
import dev.cwute.messagingapp.exception.UnauthorizedUser;
import dev.cwute.messagingapp.exception.UserNotFound;
import dev.cwute.messagingapp.repository.MessageRepository;
import dev.cwute.messagingapp.repository.UserAccountRepository;
import dev.cwute.messagingapp.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/** Service class for handling message related operations. */
@Service
@Slf4j
public class MessageServiceImpl extends UserSecurityBase implements MessageService {

  private final MessageRepository messageRepository;

  public MessageServiceImpl(
      UserAccountRepository userAccountRepository,
      PasswordEncoder passwordEncoder,
      MessageRepository messageRepository) {
    super(userAccountRepository, passwordEncoder);
    this.messageRepository = messageRepository;
  }

  /**
   * Sends a message.
   *
   * @param messageDto the message to be created
   * @param httpServletRequest the HTTP request (used for authentication and user lookup)
   * @return the created message
   */
  @Override
  public long send(MessageDto messageDto, HttpServletRequest httpServletRequest) {
    var username = checkCredentials(httpServletRequest);

    var message = messageDto.toMessage();
    var sender = userAccountRepository.findByUsername(username);
    var recipients = userAccountRepository.findAllByUsernameIn(messageDto.getRecipients());

    message.setSender(sender.orElseThrow(() -> new UserNotFound("Sender not found")));
    message.setSenderName(sender.get().getUsername());
    List<UserAccount> recipientList = (List<UserAccount>) recipients;
    message.setRecipients(recipientList);

    var recipientNames = recipientList.stream().map(UserAccount::getUsername).toList();
    message.setRecipientNames(recipientNames.toString().replace("[", "").replace("]", ""));

    var savedMessage = messageRepository.save(message);
    return savedMessage.getId();
  }

  /**
   * Gets a list of messages received by user.
   *
   * @param httpServletRequest the HTTP request (used for authentication and user lookup)
   * @return the retrieved messages
   */
  @Override
  public List<MessageView> getReceivedMessagesForUser(HttpServletRequest httpServletRequest) {
    var username = checkCredentials(httpServletRequest);
    var user =
        userAccountRepository
            .findByUsername(username)
            .orElseThrow(() -> new UserNotFound("User not found"));
    return user.getReceivedMessages().stream().map(Message::toMessageView).toList();
  }

  /**
   * Gets a list of sent messages for user.
   *
   * @param httpServletRequest the HTTP request (used for authentication and user lookup)
   * @return the retrieved messages
   */
  @Override
  public List<MessageView> getSentMessagesForUser(HttpServletRequest httpServletRequest) {
    var username = checkCredentials(httpServletRequest);
    var user =
        userAccountRepository
            .findByUsername(username)
            .orElseThrow(() -> new UserNotFound("User not found"));
    return user.getSentMessages().stream().map(Message::toMessageView).toList();
  }

  /**
   * Removes a received message by id.
   *
   * @param httpServletRequest the HTTP request (used for authentication and user lookup)
   * @param messageId
   */
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

  /**
   * Removes a sent message by id.
   *
   * @param httpServletRequest the HTTP request (used for authentication and user lookup)
   * @param messageId
   */
  @Override
  public void deleteSentMessage(HttpServletRequest httpServletRequest, long messageId) {
    var username = checkCredentials(httpServletRequest);
    log.info("Trying to remove sent message: {} for user: {}", messageId, username);
    userAccountRepository
        .findByUsername(username)
        .orElseThrow(() -> new UserNotFound("User not found"));

    var message =
        messageRepository.findById(messageId).orElseThrow(() -> new MessageNotFound("Not found"));

    if (message.getSender() == null || !message.getSender().getUsername().equals(username)) {
      throw new UnauthorizedUser("You are not the sender of this message.");
    }

    message.setSender(null);
    messageRepository.save(message);
  }
}
