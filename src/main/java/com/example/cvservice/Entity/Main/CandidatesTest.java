package com.example.cvservice.Entity.Main;


import com.example.cvservice.Entity.Grade.TestGrade;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CandidatesTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    private Candidate candidate;

    @Embedded
    private Test test;

    @Embedded
    private TestGrade grade;



}
