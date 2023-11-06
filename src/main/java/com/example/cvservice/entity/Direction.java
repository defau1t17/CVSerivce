package com.example.cvservice.entity;

import com.example.cvservice.dto.UpdateDirectionDTO;
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
@Table(name = "directions")
@NoArgsConstructor
@AllArgsConstructor
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

    public Direction update(UpdateDirectionDTO updateDirectionDTO) {
        if (updateDirectionDTO.getName() != null || !updateDirectionDTO.getName().trim().isEmpty()) {
            this.setName(updateDirectionDTO.getName());
        }
        if (updateDirectionDTO.getDescription() != null) {
            this.setDescription(updateDirectionDTO.getDescription());
        }
        return this;
    }
}
