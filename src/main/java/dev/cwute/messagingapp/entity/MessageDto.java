package dev.cwute.messagingapp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class MessageDto {
  private String subject;
  private String message;
  private String sender;
  private String[] recipients;

  public Message toMessage() {
    return Message.builder().subject(subject).message(message).build();
  }
}
