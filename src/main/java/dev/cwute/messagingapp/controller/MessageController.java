package dev.cwute.messagingapp.controller;

import dev.cwute.messagingapp.entity.MessageDto;
import dev.cwute.messagingapp.entity.MessageView;
import dev.cwute.messagingapp.entity.UserAccount;
import dev.cwute.messagingapp.exception.UnauthorizedUser;
import dev.cwute.messagingapp.service.MessageService;
import dev.cwute.messagingapp.service.UserAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
@AllArgsConstructor
@Slf4j
public class MessageController {

    private final MessageService messageService;

    private final UserAccountService userAccountService;

    @PostMapping("/send")
    public void sendMessage(@RequestBody MessageDto messageDto) {
        /*if(userAccountService.checkCredentials(userAccount)) {

        }
        throw new UnauthorizedUser("User is not authorized");*/
        log.info("MessageDto: {}", messageDto);
        messageService.send(messageDto);
    }

    @GetMapping("/{username}/received")
    public List<MessageView> getReceivedMessages(@PathVariable String username) {
        return messageService.getMessagesForUser(username);
    }
}
