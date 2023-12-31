package com.example.cvservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class CandidateFilterDTO {

    private String name;

    private String second_name;

    private String patr;

    private String candidateDesc;

    private List<String> specializationNames;

}
