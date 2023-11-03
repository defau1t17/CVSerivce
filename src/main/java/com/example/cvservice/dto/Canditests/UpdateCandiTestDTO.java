package com.example.cvservice.dto.Canditests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCandiTestDTO {

    private Long candiTestID;

    private Long candidateID;

    private Long testID;

    private String testName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private int mark;
}
