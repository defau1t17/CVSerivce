package com.example.cvservice.dto;


import lombok.Data;

import java.util.List;

@Data
public class TestFilterDTO {

    private String name;

    private String description;

    private List<String> directionNames;


}
