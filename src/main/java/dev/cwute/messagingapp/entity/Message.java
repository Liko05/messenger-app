package dev.cwute.messagingapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;
    private String message;

    @ManyToOne
    private UserAccount sender;

    @ManyToMany
    @JoinTable(
            name = "message_recipients",
            joinColumns = @JoinColumn(name = "received_messages_id"),
            inverseJoinColumns = @JoinColumn(name = "recipients_id"))
    private List<UserAccount> recipients;

    @CreationTimestamp
    private Instant timestamp;

    public MessageView toMessageView() {
        return MessageView.builder()
                .subject(subject)
                .message(message)
                .sender(sender.getUsername())
                .recipients(recipients.stream().map(UserAccount::getUsername).toList())
                .timestamp(timestamp.toString())
                .build();
    }
}
