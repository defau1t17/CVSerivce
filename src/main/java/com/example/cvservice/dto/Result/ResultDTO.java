package com.example.cvservice.dto.Result;

import com.example.cvservice.service.Candidate.CandidateService;
import com.example.cvservice.service.Test.TestService;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class ResultDTO {

    private Long candidateID;

    private Long testID;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private int mark;

    public boolean isValid(CandidateService candidateService, TestService testService) {
        if (!candidateService.findCandidateByID(this.getCandidateID()).isPresent()) {
            return false;
        }
        if (!testService.findTestByID(this.getTestID()).isPresent()) {
            return false;
        }
        if (this.getDate() == null) {
            return false;
        }
        if (this.getMark() < 0 || this.getMark() > 100) {
            return false;
        }
        return true;
    }


}
