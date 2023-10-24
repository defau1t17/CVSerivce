package com.example.cvservice.Entity.Files;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
@Embeddable
public class Image {

    @Lob
    private byte[] imageData;

}
