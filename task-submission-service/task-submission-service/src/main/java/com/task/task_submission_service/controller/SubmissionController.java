package com.task.task_submission_service.controller;

import com.task.task_submission_service.dto.ProfileDto;
import com.task.task_submission_service.model.Submission;
import com.task.task_submission_service.service.LoginService;
import com.task.task_submission_service.service.SubmissionService;
import com.task.task_submission_service.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
@Slf4j
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private TaskService taskService;

    @PostMapping("/submit")
    public ResponseEntity<Submission> submitTask(
            @RequestParam Long user_id,
            @RequestParam Long task_id,
            @RequestParam String github_link,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{

        log.info("started executing endpoint /submit");

        Submission submission=submissionService.submitTask(task_id,github_link,user_id,jwt);

        return ResponseEntity.status(HttpStatus.CREATED).body(submission);
    }

    @GetMapping("/subId/{id}")
    public ResponseEntity<Submission> getSubmissionById(
            @PathVariable("id") Long id,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{

        log.info("started executing endpoint /subId/{id}");

        Submission submission=submissionService.getTaskSubmissionById(id);

        return ResponseEntity.status(HttpStatus.OK).body(submission);
    }

    @GetMapping("/allSub")
    public ResponseEntity<List<Submission>> getAllSubmission(
            @RequestHeader("Authorization") String jwt
    ) throws Exception{

        log.info("started executing endpoint /allSub");

        List<Submission> submission=submissionService.getAllTaskSubmission();

        return ResponseEntity.status(HttpStatus.OK).body(submission);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<Submission>> getAllSubmissionByTaskId(
            @PathVariable("taskId") Long taskId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{

        log.info("started executing endpoint /task/{taskId}");

        List<Submission> submission=submissionService.getTaskSubmissionByTaskId(taskId);

        return ResponseEntity.status(HttpStatus.OK).body(submission);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Submission> acceptOrDeclineSubmission(
            @PathVariable("id") Long id,
            @RequestParam("status") String status
    )throws Exception{
        log.info("started executing endpoint /update/{id}");

        Submission submission=submissionService.acceptDeclineSubmission(id,status);

        return ResponseEntity.status(HttpStatus.OK).body(submission);
    }
}
