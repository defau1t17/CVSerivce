package com.example.cvservice.DTO.Test;

import com.example.cvservice.Entity.Main.Direction;
import lombok.Data;

import java.util.ArrayList;

@Data
public class NewTestDTO {

    private String name;

    private String description;

    private ArrayList<Direction> testDirections;
}
