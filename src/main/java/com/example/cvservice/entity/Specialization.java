package com.example.cvservice.entity;

import com.example.cvservice.dto.UpdateSpecializationDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@Table(name = "specializations")
@NoArgsConstructor
@AllArgsConstructor
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotBlank
    @Column(name = "specializationNames", unique = true)
    private String name;

    @NotNull
    @Column(name = "specializationDescription", columnDefinition = "TEXT")
    private String description;


    public Specialization(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Specialization update(UpdateSpecializationDTO updateSpecializationDTO) {
        if (updateSpecializationDTO.getName() != null || !updateSpecializationDTO.getName().trim().isEmpty()) {
            this.setName(updateSpecializationDTO.getName());
        }
        if (updateSpecializationDTO.getDescription() != null) {
            this.setDescription(updateSpecializationDTO.getDescription());
        }
        return this;
    }
}
