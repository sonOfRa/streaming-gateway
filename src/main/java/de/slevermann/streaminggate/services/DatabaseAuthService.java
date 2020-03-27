package de.slevermann.streaminggate.services;

import de.slevermann.streaminggate.config.UserConfig;
import de.slevermann.streaminggate.entities.User;
import de.slevermann.streaminggate.repositories.UserRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnMissingBean(UserConfig.class)
public class DatabaseAuthService implements AuthService {

    private UserRepository userRepository;

    public DatabaseAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @NonNull
    @Override
    public AuthResult authenticateUser(@NonNull String userName, @NonNull String passKey) {
        User user = userRepository.findByName(userName);

        if (user == null) {
            return AuthResult.UNKNOWN_USER;
        }

        String expectedKey = user.getPassKey();
        if (passKey.equals(expectedKey)) {
            return AuthResult.SUCCESS;
        } else {
            return AuthResult.BAD_KEY;
        }
    }
}
