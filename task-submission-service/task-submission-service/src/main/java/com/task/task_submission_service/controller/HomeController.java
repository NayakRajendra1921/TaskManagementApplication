package com.task.task_submission_service.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")
    public ResponseEntity<String> homeController(){
        return ResponseEntity.status(HttpStatus.OK).body("Welcome to submission service");
    }
}
