package com.example.cvservice.service.CandidateTest;

import com.example.cvservice.dto.Canditests.UpdateCandiTestDTO;
import com.example.cvservice.entity.main.CandidatesTest;

public class UpdateCandidateTestData {
    public static CandidatesTest updateCandidateTest(CandidatesTest candidatesTest, UpdateCandiTestDTO updateCandiTestDTO) {
        if (updateCandiTestDTO.getDate() != null) {
            candidatesTest.getGrade().setDate(updateCandiTestDTO.getDate());
        }

        if (updateCandiTestDTO.getMark() >= 0 && updateCandiTestDTO.getMark() <= 100) {
            candidatesTest.getGrade().setMark(updateCandiTestDTO.getMark());
        }

        return candidatesTest;
    }
}
