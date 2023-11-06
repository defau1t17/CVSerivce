package com.example.cvservice.dto;

import com.example.cvservice.entity.Direction;
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

    private List<Direction> testDirections;

    public boolean isValid() {
        if (this.getName() == null || this.getName().isEmpty()) {
            return false;
        }
        if (this.getTestDirections() == null || this.getTestDirections().isEmpty()) {
            return false;
        }
        return true;
    }
}
