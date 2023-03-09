package dev.cwute.messagingapp.service;

import dev.cwute.messagingapp.entity.MessageDto;
import dev.cwute.messagingapp.entity.MessageView;

import java.util.List;

public interface MessageService {
    long send(MessageDto messageDto);
    List<MessageView> getMessagesForUser(String username);
}
