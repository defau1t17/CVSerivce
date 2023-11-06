package com.example.cvservice.dto;

import com.example.cvservice.entity.Specialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTestDTO {

    private Long id;

    private String name;

    private String description;

    private List<Specialization> testSpecializations;

    public boolean isValid() {
        if (this.getName() == null || this.getName().isEmpty()) {
            return false;
        }
        if (this.getTestSpecializations() == null || this.getTestSpecializations().isEmpty()) {
            return false;
        }
        return true;
    }
}
