package com.example.cvservice.dto.Candidate;

import com.example.cvservice.entity.main.Direction;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class NewCandidateDTO {

    private String name;

    private String second_name;

    private String patr;

    private String candidateDesc;

    private MultipartFile cvFile;

    private MultipartFile imageFile;

    private List<Direction> directions;


}
