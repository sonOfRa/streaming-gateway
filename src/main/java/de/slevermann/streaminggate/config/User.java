package de.slevermann.streaminggate.config;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class User {
    @NotBlank
    private String userName;
    @NotBlank
    private String passKey;
}
