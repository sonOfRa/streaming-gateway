package de.slevermann.streaminggate.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static de.slevermann.streaminggate.services.AuthResult.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ConfigAuthServiceTest {

    @Autowired
    private ConfigAuthService configAuthService;

    @Test
    public void testCorrectPassKey() {
        AuthResult result = configAuthService.authenticateUser("alice", "passKeyAlice");

        assertEquals(SUCCESS, result, "Correct pass key should result in success");
    }

    @Test
    public void testUnknownUser() {
        AuthResult result = configAuthService.authenticateUser("unknown", "password");

        assertEquals(UNKNOWN_USER, result, "Unknown user should result in unknown user code");
    }


    @Test
    public void testBadPassKey() {
        AuthResult result = configAuthService.authenticateUser("alice", "wrong");

        assertEquals(BAD_KEY, result, "Incorrect passkey should return bad key");
    }

    @Test
    public void testOtherUserPassKey() {
        AuthResult result = configAuthService.authenticateUser("alice", "passKeyBob");

        assertEquals(BAD_KEY, result, "Passkey for other user should result in bad key");
    }
}
