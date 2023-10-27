package com.example.cvservice.Entity.Files;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.Data;
import org.apache.tomcat.util.codec.binary.Base64;

@Embeddable
@Data
public class Image {

    private String imageFileName;
    @Lob
    private byte[] imageData;

    public String generateBase64Image() {
        return Base64.encodeBase64String(this.imageData);
    }

}
