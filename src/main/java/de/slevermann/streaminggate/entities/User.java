package de.slevermann.streaminggate.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class User {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long id;

    private String userName;

    private String password;

    private String passKey;

    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private Role role;
}
