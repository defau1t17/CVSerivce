package com.example.cvservice.dto.Direction;


import com.example.cvservice.entity.main.Direction;
import lombok.Data;

@Data
public class UpdateDirectionDTO {

    private Long id;

    private String name;

    private String description;

    public boolean isValid() {
        if (this.getName() == null || this.getName().isEmpty()) {
            return false;
        }
        return true;
    }

}
