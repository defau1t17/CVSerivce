package com.example.cvservice.dto;

import com.example.cvservice.entity.Specialization;
import lombok.Data;

import java.util.ArrayList;

@Data
public class NewTestDTO {

    private String name;

    private String description;

    private ArrayList<Specialization> testSpecializations;


    public boolean isValid() {
        if (this.getName() == null || this.getName().isEmpty()) {
            return false;
        }
        if (this.getDescription() == null) {
            return false;
        }
        if (this.getTestSpecializations() == null || this.getTestSpecializations().isEmpty()) {
            return false;
        }
        return true;
    }
}
