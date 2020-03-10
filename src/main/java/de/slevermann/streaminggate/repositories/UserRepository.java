package de.slevermann.streaminggate.repositories;

import de.slevermann.streaminggate.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);
}
