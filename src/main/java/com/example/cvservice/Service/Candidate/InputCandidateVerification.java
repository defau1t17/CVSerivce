package com.example.cvservice.Service.Candidate;

import com.example.cvservice.DTO.Candidate.NewCandidateDTO;

public class InputCandidateVerification {
    public static boolean doesCandidateIsEmpty(NewCandidateDTO candidateDTO) {
        if (candidateDTO.getName().trim().isEmpty()) {
            return true;
        }
        if (candidateDTO.getSecond_name().trim().isEmpty()) {
            return true;
        }

        if (candidateDTO.getPatr().trim().isEmpty()) {
            return true;

        }
        if (candidateDTO.getDirections().size() == 0) {
            return true;
        }

        return false;
    }

}
