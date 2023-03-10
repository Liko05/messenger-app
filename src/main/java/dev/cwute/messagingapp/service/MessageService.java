package dev.cwute.messagingapp.service;

import dev.cwute.messagingapp.entity.MessageDto;
import dev.cwute.messagingapp.entity.MessageView;

import java.util.List;

public interface MessageService {
  long send(MessageDto messageDto);

  List<MessageView> getReceivedMessagesForUser(String username);

  List<MessageView> getSentMessagesForUser(String username);

  void removeReceivedMessage(String username, long messageId);

  void deleteSentMessage(String username, long messageId);
}
