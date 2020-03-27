package de.slevermann.streaminggate.services;

import de.slevermann.streaminggate.config.UserConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnBean(UserConfig.class)
public class ConfigAuthService implements AuthService {

    private final UserConfig userConfig;

    public ConfigAuthService(UserConfig userConfig) {
        this.userConfig = userConfig;
    }

    @NonNull
    @Override
    public AuthResult authenticateUser(@NonNull String userName, @NonNull String passKey) {
        String expectedKey = userConfig.getPassKeys().get(userName);

        if (expectedKey == null) {
            return AuthResult.UNKNOWN_USER;
        }

        if (passKey.equals(expectedKey)) {
            return AuthResult.SUCCESS;
        } else {
            return AuthResult.BAD_KEY;
        }
    }
}
