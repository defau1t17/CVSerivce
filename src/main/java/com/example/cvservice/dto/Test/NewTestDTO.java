package com.example.cvservice.dto.Test;

import com.example.cvservice.entity.main.Direction;
import lombok.Data;

import java.util.ArrayList;

@Data
public class NewTestDTO {

    private String name;

    private String description;

    private ArrayList<Direction> testDirections;
}
