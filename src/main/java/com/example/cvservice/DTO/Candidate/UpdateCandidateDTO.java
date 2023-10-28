package com.example.cvservice.DTO.Candidate;

import com.example.cvservice.Entity.Main.Direction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCandidateDTO {

    private Long id;

    private String name;

    private String second_name;

    private String patr;

    private String candidateDesc;

    private MultipartFile cvFile;
    private MultipartFile imageFile;

    private List<Direction> directions;

    public String generateBase64Image() throws IOException {
        return Base64.encodeBase64String(this.imageFile.getBytes());
    }


}
