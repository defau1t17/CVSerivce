package com.example.cvservice.Entity.Main;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Data
@Table(name = "directions")
@NoArgsConstructor
public class Direction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotBlank
    @Column(name = "directionName", unique = true)
    private String name;

    @NotNull
    @Column(name = "directionDescription", columnDefinition = "TEXT")
    private String description;


    public Direction(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
