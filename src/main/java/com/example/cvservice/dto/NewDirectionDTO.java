package com.example.cvservice.dto;


import lombok.Data;

@Data
public class NewDirectionDTO {

    private String name;

    private String description;

    public boolean isValid() {
        if (this.getName() == null || this.getName().isEmpty()) {
            return false;
        }
        if (this.getDescription() == null) {
            return false;
        }
        return true;
    }
}
