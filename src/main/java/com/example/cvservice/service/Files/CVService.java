package com.example.cvservice.service.Files;


import com.example.cvservice.entity.files.CurriculumVitae;
import com.example.cvservice.entity.files.Image;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class CVService {

    public  Image buildImage(MultipartFile multipartFile) {
        Image image = new Image();
        if (multipartFile != null && !multipartFile.isEmpty()) {
            try {
                image.setImageData(multipartFile.getBytes());
                image.setImageFileName(multipartFile.getOriginalFilename());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {

            File defaultImage = new File("src/main/resources/static/images/default-image.png");
            try {
                byte[] defaultImageData = Files.readAllBytes(defaultImage.toPath());
                image.setImageData(defaultImageData);
                image.setImageFileName(defaultImage.getName());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return image;
    }

    public  CurriculumVitae buildCV(MultipartFile multipartFile) {
        CurriculumVitae curriculumVitae = new CurriculumVitae();
        if (multipartFile != null && !multipartFile.isEmpty()) {
            try {
                curriculumVitae.setCvFileName(multipartFile.getOriginalFilename());
                curriculumVitae.setCvData(multipartFile.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            curriculumVitae.setCvFileName(null);
            curriculumVitae.setCvData(null);
        }
        return curriculumVitae;
    }

    public static MultipartFile createMultipartFileFormByteArray(byte[] data, String contentType, String fileName) {
        return new MockMultipartFile(fileName, fileName, contentType, data);
    }


}
