package com.example.cvservice.DAO;

import com.example.cvservice.Entity.Main.Direction;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CandidateDAO {

    private String name;

    private String second_name;

    private String patr;

    private String candidateDesc;

    private List<String> directionNames;
}
