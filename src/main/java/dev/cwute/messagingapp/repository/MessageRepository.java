package dev.cwute.messagingapp.repository;

import dev.cwute.messagingapp.entity.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/** MessageRepository */
public interface MessageRepository extends JpaRepository<Message, Long> {}
