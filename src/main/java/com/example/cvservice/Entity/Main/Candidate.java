package com.example.cvservice.Entity.Main;

import com.example.cvservice.Entity.Files.CurriculumVitae;
import com.example.cvservice.Entity.Files.Image;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.ArrayList;

@Data
@Entity
@Table(name = "candidates")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    @NotBlank
    private String name;

    @NotEmpty
    @NotBlank
    private String secondName;

    @NotEmpty
    @NotBlank
    private String patronymic;

    @Embedded
    private Image image;

    @Embedded
    private CurriculumVitae curriculumVitae;

    @OneToMany
    private ArrayList<Direction> directions;

}
