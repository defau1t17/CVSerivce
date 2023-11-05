package com.example.cvservice.service;

import com.example.cvservice.entity.main.Candidate;
import com.example.cvservice.entity.main.Direction;
import com.example.cvservice.entity.main.Test;
import com.example.cvservice.service.Candidate.CandidateService;
import com.example.cvservice.service.Direction.DirectionService;
import com.example.cvservice.service.Test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private DirectionService directionService;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private TestService testService;

    @Override
    public void run(String... args) throws Exception {
//        directionService.saveIfNotExists(new Direction(0L, "backend", "test"));
    

//        directionService.save(ios);
//        directionService.save(frontend);
//        directionService.save(design);


//        List<Direction> directions = new ArrayList<>();
//        directions.add(backend);
//        directions.add(android);
//        Candidate candidate = Candidate.builder()
//                .name("Test")
//                .secondName("Test")
//                .patronymic("Test")
//                .candidateDescription("Test")
//                .directions(directions)
//                .cv(null)
//                .image(null)
//                .build();
//        Test test = Test.builder()
//                .name("Test")
//                .description("Java Test")
//                .directions(directions)
//                .build();
//
//        testService.save(test);
//        candidateService.save(candidate);
//
//
//        directions.add(android);
//        directions.add(ios);
//        Candidate candidate1 = Candidate.builder()
//                .name("Test")
//                .secondName("Test")
//                .patronymic("Test")
//                .candidateDescription("Test")
//                .directions(directions)
//                .cv(null)
//                .image(null)
//                .build();
//
//        Test test2 = Test.builder()
//                .name("Test 2")
//                .description("Global Test")
//                .directions(directions)
//                .build();
//
//        testService.save(test2);
//        candidateService.save(candidate1);

    }


}
