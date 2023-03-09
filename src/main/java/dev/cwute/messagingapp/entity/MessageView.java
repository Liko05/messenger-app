package dev.cwute.messagingapp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class MessageView {
    private String subject;
    private String message;
    private String sender;
    private List<String> recipients;
    private String timestamp;
}