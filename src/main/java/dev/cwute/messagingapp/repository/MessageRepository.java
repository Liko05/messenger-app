package dev.cwute.messagingapp.repository;

import dev.cwute.messagingapp.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageRepository extends JpaRepository<Message, Long> {}
