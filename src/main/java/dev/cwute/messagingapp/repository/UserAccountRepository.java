package dev.cwute.messagingapp.repository;

import dev.cwute.messagingapp.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for UserAccount entity.
 */
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByUsername(String username);

    Iterable<UserAccount> findAllByUsernameIn(String[] usernames);
}
