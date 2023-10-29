package com.example.cvservice.Service.CandidateTest;

import com.example.cvservice.DTO.Canditests.CandiTestDTO;
import com.example.cvservice.DTO.Canditests.UpdateCandiTestDTO;
import com.example.cvservice.Entity.Grade.TestGrade;
import com.example.cvservice.Entity.Main.CandidatesTest;

public class InputCandidateTestValidation {
    public static boolean isCandidateTestExists(CandidateTestsService candidateTestsService, CandidatesTest candidatesTest) {
        return candidateTestsService.isCandiTestsExists(candidatesTest).isPresent();
    }

    public static boolean isCandidateTestEmpty(CandidatesTest candidatesTest) {
        if (candidatesTest.getTest() == null) {
            return true;
        }

        if (candidatesTest.getCandidate() == null) {
            return true;
        }

        if (candidatesTest.getGrade() == null) {
            return true;
        }

        if (candidatesTest.getGrade().getDate() == null) {
            return true;
        }
        return false;
    }

    public static boolean isUpdateCandidateTestDataEmpty(UpdateCandiTestDTO updateCandiTestDTO) {
        if (updateCandiTestDTO.getDate() == null) {
            return true;
        }
        if (updateCandiTestDTO.getTestID() == null) {
            return true;
        }

        if (updateCandiTestDTO.getCandiTestID() == null) {
            return true;
        }
        if (updateCandiTestDTO.getCandidateID() == null) {
            return true;
        }

        if (updateCandiTestDTO.getMark() < 0 || updateCandiTestDTO.getMark() > 100) {
            return true;
        }

        return false;
    }

}
