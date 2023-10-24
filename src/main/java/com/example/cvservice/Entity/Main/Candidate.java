package com.example.cvservice.Entity.Main;

import com.example.cvservice.Entity.Files.CurriculumVitae;
import com.example.cvservice.Entity.Files.Image;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;

@Entity
@Embeddable
@Data
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String secondName;

    private String patronymic;

    @Embedded
    private Image image;

    @Embedded
    private CurriculumVitae curriculumVitae;

    @ElementCollection
    private ArrayList<Direction> directions;


}
