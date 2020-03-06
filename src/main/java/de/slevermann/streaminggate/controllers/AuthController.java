package de.slevermann.streaminggate.controllers;

import de.slevermann.streaminggate.config.UserConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@RestController
public class AuthController {

    private final UserConfig userConfig;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(UserConfig userConfig) {
        this.userConfig = userConfig;
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public void auth(@RequestParam("name") String name, @RequestParam("key") String key) {
        logger.info("Received auth request from " + name);

        String expectedKey = userConfig.getUsers().get(name);
        if (expectedKey == null) {
            logger.info("User " + name + " does not exist.");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        if (!Objects.equals(expectedKey, key)) {
            logger.info("Incorrect authentication key for " + name);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        logger.info("Auth success for " + name);
    }
}
