package com.example.cvservice.dto.Result;

import com.example.cvservice.service.Candidate.CandidateService;
import com.example.cvservice.service.Result.ResultService;
import com.example.cvservice.service.Test.TestService;
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
public class UpdateResultDTO {

    private Long resultID;

    private Long candidateID;

    private Long testID;

    private String testName;

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
