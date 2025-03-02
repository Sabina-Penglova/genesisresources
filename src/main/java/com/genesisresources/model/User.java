package com.genesisresources.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Jméno nesmí být prázdné.")
    private String name;

    @NotBlank(message = "Příjmení nesmí být prázdné.")
    private String surname;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "PersonID nesmí být prázdné.")
    private String personId;

    @Column(unique = true, nullable = false)
    private String uuid;
}
