package com.example.cvservice.Controller.Rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/task/candidates/rest")
public class CandidateRestController {


    @GetMapping("/add/new")
    public void test() {
        System.out.println("end point");
    }


}
