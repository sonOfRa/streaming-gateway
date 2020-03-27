package de.slevermann.streaminggate.repositories;

import de.slevermann.streaminggate.config.UserConfig;
import de.slevermann.streaminggate.entities.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnMissingBean(UserConfig.class)
public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);
}
