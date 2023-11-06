package com.example.cvservice.entity;

import com.example.cvservice.dto.UpdateTestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    @NotBlank
    private String name;

    @NotNull
    private String description;

    @ManyToMany
    private List<Specialization> specializations;

    public Test update(UpdateTestDTO updateTestDTO) {
        if (updateTestDTO.getName() != null && !updateTestDTO.getName().trim().isEmpty()) {
            this.setName(updateTestDTO.getName());
        }
        if (updateTestDTO.getTestSpecializations() != null && !updateTestDTO.getTestSpecializations().isEmpty()) {
            this.setSpecializations(updateTestDTO.getTestSpecializations());
        }
        if (updateTestDTO.getDescription() != null) {
            this.setDescription(updateTestDTO.getDescription());
        }
        return this;
    }
}
