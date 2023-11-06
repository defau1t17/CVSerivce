package com.example.cvservice.dto;

import com.example.cvservice.entity.Specialization;
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

    private List<Specialization> specializations;

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
