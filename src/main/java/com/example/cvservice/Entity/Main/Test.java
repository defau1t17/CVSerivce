package com.example.cvservice.Entity.Main;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;

@Entity
@Embeddable
@Data
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String description;

    @ElementCollection
    private ArrayList<Direction> directions;

}
