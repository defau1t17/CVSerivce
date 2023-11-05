package com.example.cvservice.entity.main;

import com.example.cvservice.dto.Test.UpdateTestDTO;
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
    private List<Direction> directions;

    public Test update(UpdateTestDTO updateTestDTO) {
        if (updateTestDTO.getName() != null && !updateTestDTO.getName().trim().isEmpty()) {
            this.setName(updateTestDTO.getName());
        }
        if (updateTestDTO.getTestDirections() != null && !updateTestDTO.getTestDirections().isEmpty()) {
            this.setDirections(updateTestDTO.getTestDirections());
        }
        if (updateTestDTO.getDescription() != null) {
            this.setDescription(updateTestDTO.getDescription());
        }
        return this;
    }
}
