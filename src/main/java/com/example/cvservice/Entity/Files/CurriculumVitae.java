package com.example.cvservice.Entity.Files;


import jakarta.persistence.Column;
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
