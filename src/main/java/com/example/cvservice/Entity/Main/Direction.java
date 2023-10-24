package com.example.cvservice.Entity.Main;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Embeddable
@Table(name = "directions")
@Data
public class Direction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "directionName")
    private String name;

    @Column(name = "directionDescription")
    private String description;


}
