package com.example.cvservice.dto;

import com.example.cvservice.entity.Specialization;
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

    private List<Specialization> specializations;

    public String generateBase64Image() throws IOException {
        return Base64.encodeBase64String(this.imageFile.getBytes());
    }

    public boolean isValid() {
        if (this.getName() == null || this.getName().trim().isEmpty()) {
            return false;
        }
        if (this.getSecond_name() == null || this.getSecond_name().trim().isEmpty()) {
            return false;
        }

        if (this.getPatr() == null || this.getPatr().trim().isEmpty()) {
            return false;

        }
        if (this.getSpecializations() == null || this.getSpecializations().isEmpty()) {
            return false;
        }
        return true;
    }


}
