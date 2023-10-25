package com.example.cvservice.Service.Files;


import com.example.cvservice.Entity.Files.CurriculumVitae;
import com.example.cvservice.Entity.Files.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FileService {

    public static Image buildImage(MultipartFile multipartFile) {
        Image image = new Image();
        if (!multipartFile.isEmpty()) {
            try {
                image.setImageData(multipartFile.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            image.setImageData(null);
        }
        return image;
    }

    public static CurriculumVitae buildCV(MultipartFile multipartFile) {
        CurriculumVitae curriculumVitae = new CurriculumVitae();
        if (!multipartFile.isEmpty()) {
            try {

                curriculumVitae.setCvData(multipartFile.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            curriculumVitae.setCvData(null);
        }
        return curriculumVitae;
    }


}
