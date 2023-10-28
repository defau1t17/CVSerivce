package com.example.cvservice.Service.Test;

import com.example.cvservice.DTO.Candidate.NewCandidateDTO;
import com.example.cvservice.DTO.Test.NewTestDTO;
import com.example.cvservice.DTO.Test.UpdateTestDTO;
import com.example.cvservice.Entity.Main.Test;

import java.util.Optional;

public class InputTestValidation {

    public static boolean isNewTestEmpty(NewTestDTO newTestDTO) {
        if (newTestDTO.getName().trim().isEmpty()) {
            return true;
        }
        if (newTestDTO.getTestDirections().size() == 0) {
            return true;
        }

        return false;
    }

    public static boolean isNewTestExists(TestService testService, NewTestDTO newTestDTO) {
        Optional<Test> optionalTest = testService.findTestByName(newTestDTO.getName());
        return optionalTest.isPresent();
    }

    public static boolean isUpdateTestEmpty(UpdateTestDTO updateTestDTO) {
        if (updateTestDTO.getName().trim().isEmpty()) {
            return true;
        }

        if (updateTestDTO.getTestDirections().size() == 0) {
            return true;
        }

        return false;
    }

}
