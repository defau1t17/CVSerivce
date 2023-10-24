package com.example.cvservice.Entity.Files;


import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.Data;

@Embeddable
@Data
public class CurriculumVitae {

    @Lob
    private byte[] cvData;
}
