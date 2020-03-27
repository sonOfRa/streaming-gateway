package de.slevermann.streaminggate.services;

import org.springframework.lang.NonNull;

public interface AuthService {

    @NonNull
    AuthResult authenticateUser(@NonNull String userName, @NonNull String passKey);
}
