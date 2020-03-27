package de.slevermann.streaminggate.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "USER")
public class User {

    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PASS_KEY")
    private String passKey;
}
