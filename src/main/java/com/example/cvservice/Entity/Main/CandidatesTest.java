package com.example.cvservice.Entity.Main;


import com.example.cvservice.Entity.Grade.TestGrade;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "canditests")
public class CandidatesTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private Candidate candidate;

    @OneToOne
    private Test test;

    @Embedded
    private TestGrade grade;



}
