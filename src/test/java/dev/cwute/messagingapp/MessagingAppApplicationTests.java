package dev.cwute.messagingapp;

import dev.cwute.messagingapp.entity.UserAccount;
import dev.cwute.messagingapp.entity.message.MessageView;
import dev.cwute.messagingapp.repository.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class MessagingAppApplicationTests {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserAccountRepository userAccountRepository;

  private UserAccount dummyUserAccount;

  @Test
  void contextLoads() {}
/*
  @BeforeEach
  void setUp() {
    userAccountRepository.deleteAll();
    var acc = new UserAccount()
            .builder()
            .username("test")
            .password("test")
            .build();
    dummyUserAccount = userAccountRepository.save(acc);
  }

  @Test
  void testMessageViewPropperMapping() {
    MessageView messageView = new MessageView();
    messageView.setId(1);
    messageView.setSubject("subject");
    messageView.setMessage("message");
    messageView.setSender("sender");
    messageView.setRecipients("recipients");
    messageView.setTimestamp("timestamp");
    assert messageView.getId() == 1;
    assert messageView.getSubject().equals("subject");
    assert messageView.getMessage().equals("message");
    assert messageView.getSender().equals("sender");
    assert messageView.getRecipients().equals("recipients");
    assert messageView.getTimestamp().equals("timestamp");
  }

  @Test
  void messageSent() throws Exception {
    mockMvc.perform(post("/api/v1/messages/send")
            .header("Credentials","test:test")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                      "subject": "test",
                      "message": "test",
                      "recipients": ["test"]
                    }
"""))
            .andExpect(status().isOk());
  }*/
}
