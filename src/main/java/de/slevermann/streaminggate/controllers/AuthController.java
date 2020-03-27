package de.slevermann.streaminggate.controllers;

import de.slevermann.streaminggate.services.AuthResult;
import de.slevermann.streaminggate.services.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class AuthController {

    private final AuthService authService;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public void auth(@RequestParam("name") String name, @RequestParam("key") String key) {
        logger.info("Received auth request from " + name);

        AuthResult authResult = authService.authenticateUser(name, key);

        switch (authResult) {
            case BAD_KEY:
                logger.info("Incorrect authentication key for " + name);
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            case UNKNOWN_USER:
                logger.info("User " + name + " does not exist.");
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            case SUCCESS:
                logger.info("Auth success for " + name);
                break;
            default:
                logger.error("Unknown AuthResult");
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
