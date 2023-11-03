package com.example.cvservice.entity.files;


import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.Data;

@Embeddable
@Data
public class CurriculumVitae {

    private String cvFileName;

    @Lob
    private byte[] cvData;
}
