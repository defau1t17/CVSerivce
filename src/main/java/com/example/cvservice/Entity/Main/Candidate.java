package com.example.cvservice.Entity.Main;

import com.example.cvservice.Entity.Files.CurriculumVitae;
import com.example.cvservice.Entity.Files.Image;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String candidateDescription;

    @Embedded
    private Image image;

    @Embedded
    private CurriculumVitae curriculumVitae;

    @ManyToMany
    private List<Direction> directions;


}
