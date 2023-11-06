package com.example.cvservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private SpecializationService specializationService;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private TestService testService;

    @Override
    public void run(String... args) throws Exception {
//        directionService.saveIfNotExists(new Specialization(0L, "backend", "test"));
    

//        directionService.save(ios);
//        directionService.save(frontend);
//        directionService.save(design);


//        List<Specialization> specializations = new ArrayList<>();
//        specializations.add(backend);
//        specializations.add(android);
//        Candidate candidate = Candidate.builder()
//                .name("Test")
//                .secondName("Test")
//                .patronymic("Test")
//                .candidateDescription("Test")
//                .specializations(specializations)
//                .cv(null)
//                .image(null)
//                .build();
//        Test test = Test.builder()
//                .name("Test")
//                .description("Java Test")
//                .specializations(specializations)
//                .build();
//
//        testService.save(test);
//        candidateService.save(candidate);
//
//
//        specializations.add(android);
//        specializations.add(ios);
//        Candidate candidate1 = Candidate.builder()
//                .name("Test")
//                .secondName("Test")
//                .patronymic("Test")
//                .candidateDescription("Test")
//                .specializations(specializations)
//                .cv(null)
//                .image(null)
//                .build();
//
//        Test test2 = Test.builder()
//                .name("Test 2")
//                .description("Global Test")
//                .specializations(specializations)
//                .build();
//
//        testService.save(test2);
//        candidateService.save(candidate1);

    }


}
