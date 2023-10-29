package com.example.cvservice.DTO.Canditests;

import com.example.cvservice.DTO.Candidate.NewCandidateDTO;
import com.example.cvservice.DTO.Test.NewTestDTO;
import com.example.cvservice.Entity.Grade.TestGrade;
import com.example.cvservice.Entity.Main.Candidate;
import com.example.cvservice.Entity.Main.Test;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
