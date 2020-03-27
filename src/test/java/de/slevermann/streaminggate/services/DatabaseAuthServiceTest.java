package de.slevermann.streaminggate.services;

import de.slevermann.streaminggate.entities.User;
import de.slevermann.streaminggate.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static de.slevermann.streaminggate.services.AuthResult.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("testdb")
public class DatabaseAuthServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DatabaseAuthService databaseAuthService;

    @BeforeEach
    public void beforeEach() {
        createUsers();
    }

    @AfterEach
    public void afterEach() {
        userRepository.deleteAll();
    }

    private void createUsers() {
        User u1 = new User();
        u1.setName("alice");
        u1.setPassKey("passkeyforalice");
        User u2 = new User();
        u2.setName("bob");
        u2.setPassKey("passkeyforbob");

        userRepository.save(u1);
        userRepository.save(u2);
    }

    @Test
    public void testUnknownUser() {
        AuthResult result = databaseAuthService.authenticateUser("unknown", "passkeyforalice");
        assertEquals(UNKNOWN_USER, result, "Unknown user should return unknown user");
    }

    @Test
    public void testCorrectPassKey() {
        AuthResult result = databaseAuthService.authenticateUser("alice", "passkeyforalice");
        assertEquals(SUCCESS, result, "Correct passkey should yield correct result");
    }

    @Test
    public void testIncorrectPassKey() {
        AuthResult result = databaseAuthService.authenticateUser("alice", "wrong");
        assertEquals(BAD_KEY, result, "Wrong passkey should result in bad key");
    }

    @Test
    public void testOtherUserPassKey() {
        AuthResult result = databaseAuthService.authenticateUser("alice", "passkeyforbob");
        assertEquals(BAD_KEY, result, "Passkey for other user should not allow authentication");
    }
}
