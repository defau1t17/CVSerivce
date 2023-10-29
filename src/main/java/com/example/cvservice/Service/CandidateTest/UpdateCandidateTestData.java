package com.example.cvservice.Service.CandidateTest;

import com.example.cvservice.DTO.Canditests.UpdateCandiTestDTO;
import com.example.cvservice.Entity.Main.CandidatesTest;

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
