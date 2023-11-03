package com.example.cvservice.dto.Test;

import com.example.cvservice.entity.main.Direction;
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
