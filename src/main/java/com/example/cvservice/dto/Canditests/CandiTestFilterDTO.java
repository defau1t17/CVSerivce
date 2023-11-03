package com.example.cvservice.dto.Canditests;

import com.example.cvservice.dto.Candidate.CandidateFilterDTO;
import com.example.cvservice.dto.Test.TestFilterDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class CandiTestFilterDTO {
    private CandidateFilterDTO candidateFilterDTO;
    private TestFilterDTO testFilterDTO;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate toDate;
    @Min(value = 0)
    private int fromMark;
    @Max(value = 100)
    private int toMark;
}
