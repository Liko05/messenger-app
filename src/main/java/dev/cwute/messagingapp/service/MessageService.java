package dev.cwute.messagingapp.service;

import dev.cwute.messagingapp.entity.MessageDto;
import dev.cwute.messagingapp.entity.MessageView;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface MessageService {
    long send(MessageDto messageDto, HttpServletRequest request);
    List<MessageView> getMessagesForUser(String username);
}
