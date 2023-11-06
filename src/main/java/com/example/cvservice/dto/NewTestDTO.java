package com.example.cvservice.dto;

import com.example.cvservice.entity.Direction;
import lombok.Data;

import java.util.ArrayList;

@Data
public class NewTestDTO {

    private String name;

    private String description;

    private ArrayList<Direction> testDirections;


    public boolean isValid() {
        if (this.getName() == null || this.getName().isEmpty()) {
            return false;
        }
        if (this.getDescription() == null) {
            return false;
        }
        if (this.getTestDirections() == null || this.getTestDirections().isEmpty()) {
            return false;
        }
        return true;
    }
}
