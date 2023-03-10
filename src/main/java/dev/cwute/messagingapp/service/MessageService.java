package dev.cwute.messagingapp.service;

import dev.cwute.messagingapp.entity.message.MessageDto;
import dev.cwute.messagingapp.entity.message.MessageView;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface MessageService {
  long send(MessageDto messageDto, HttpServletRequest httpServletRequest);

  List<MessageView> getReceivedMessagesForUser(HttpServletRequest httpServletRequest);

  List<MessageView> getSentMessagesForUser(HttpServletRequest httpServletRequest);

  void removeReceivedMessage(HttpServletRequest httpServletRequest, long messageId);

  void deleteSentMessage(HttpServletRequest httpServletRequest, long messageId);
}
