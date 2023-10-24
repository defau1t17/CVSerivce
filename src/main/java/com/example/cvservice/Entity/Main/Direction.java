package com.example.cvservice.Entity.Main;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
@Table(name = "directions")
public class Direction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotBlank
    @Column(name = "directionName")
    private String name;

    @NotEmpty
    @NotBlank
    @Column(name = "directionDescription")
    private String description;


}
