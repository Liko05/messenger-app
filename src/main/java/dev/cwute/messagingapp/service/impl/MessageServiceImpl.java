package dev.cwute.messagingapp.service.impl;

import dev.cwute.messagingapp.entity.Message;
import dev.cwute.messagingapp.entity.MessageDto;
import dev.cwute.messagingapp.entity.MessageView;
import dev.cwute.messagingapp.entity.UserAccount;
import dev.cwute.messagingapp.exception.UserNotFound;
import dev.cwute.messagingapp.repository.MessageRepository;
import dev.cwute.messagingapp.repository.UserAccountRepository;
import dev.cwute.messagingapp.service.MessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserAccountRepository userAccountRepository;

    @Override
    public long send(MessageDto messageDto) {
        var message = messageDto.toMessage();
        var sender = userAccountRepository.findByUsername(messageDto.getSender());
        log.info("Sender: {}", sender.get().getUsername());
        log.info("recipients: {}", messageDto.getRecipients());
        var recipients = userAccountRepository.findAllByUsernameIn(messageDto.getRecipients());

        message.setSender(sender.orElseThrow(() -> new UserNotFound("Sender not found")));
        List<UserAccount> recipientList = (List<UserAccount>) recipients;
        message.setRecipients(recipientList);

        var savedMessage = messageRepository.save(message);
        return savedMessage.getId();
    }

    @Override
    public List<MessageView> getMessagesForUser(String username) {
        var user = userAccountRepository.findByUsername(username).orElseThrow(() -> new UserNotFound("User not found"));
        return user.getReceivedMessages().stream().map(Message::toMessageView).toList();
    }
}
