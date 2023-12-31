package com.example.cvservice.entity;

import com.example.cvservice.dto.UpdateCandidateDTO;
import com.example.cvservice.service.CVService;
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
    private CurriculumVitae cv;

    @ManyToMany
    private List<Specialization> specializations;

    public Candidate update(UpdateCandidateDTO updateCandidateDTO, CVService cvService) {
        if (updateCandidateDTO.getName() != null && !updateCandidateDTO.getName().trim().isEmpty()) {
            this.setName(updateCandidateDTO.getName());
        }
        if (updateCandidateDTO.getSecond_name() != null && !updateCandidateDTO.getSecond_name().trim().isEmpty()) {
            this.setSecondName(updateCandidateDTO.getSecond_name());
        }
        if (updateCandidateDTO.getPatr() != null && !updateCandidateDTO.getPatr().trim().isEmpty()) {
            this.setPatronymic(updateCandidateDTO.getPatr());
        }
        if (updateCandidateDTO.getCandidateDesc() != null) {
            this.setCandidateDescription(updateCandidateDTO.getCandidateDesc());
        }
        if (updateCandidateDTO.getCvFile() != null && !updateCandidateDTO.getCvFile().isEmpty()) {
            this.setCv(cvService.buildCV(updateCandidateDTO.getCvFile()));
        }
        if (updateCandidateDTO.getImageFile() != null && !updateCandidateDTO.getImageFile().isEmpty()) {
            this.setImage(cvService.buildImage(updateCandidateDTO.getImageFile()));
        }
        if (updateCandidateDTO.getSpecializations() != null && updateCandidateDTO.getSpecializations().size() != 0) {
            this.setSpecializations(updateCandidateDTO.getSpecializations());
        }
        return this;
    }

}
