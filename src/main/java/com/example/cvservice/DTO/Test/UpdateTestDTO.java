package com.example.cvservice.DTO.Test;

import com.example.cvservice.Entity.Main.Direction;
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

}
