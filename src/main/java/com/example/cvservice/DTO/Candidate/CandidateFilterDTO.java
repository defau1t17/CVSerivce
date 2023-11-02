package com.example.cvservice.DTO.Candidate;

import com.example.cvservice.Entity.Main.Direction;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CandidateFilterDTO {

    private String name;

    private String second_name;

    private String patr;

    private String candidateDesc;

    private List<String> directionNames;
}
