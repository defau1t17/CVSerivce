package com.example.cvservice.dto.Canditests;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

    @Data
    public class CandiTestDTO {

        private Long candidateID;

        private Long testID;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate date;

        private int mark;


    }
