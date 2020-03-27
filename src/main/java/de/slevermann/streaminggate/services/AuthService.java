package de.slevermann.streaminggate.services;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    @NonNull
    AuthResult authenticateUser(@NonNull String userName, @NonNull String passKey);
}
