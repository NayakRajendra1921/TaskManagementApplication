package com.task.task_submission_service.service;

import com.task.task_submission_service.model.Submission;
import com.task.task_submission_service.repo.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubmissionServiceImplementation implements SubmissionService{

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private TaskService taskService;


    @Override
    public Submission submitTask(Long taskId, String githubLink, Long userId,String jwt) throws Exception {

        Task task=taskService.getTaskById(jwt,taskId);
        if(task!=null){
            Submission submission=Submission.builder()
                    .taskId(taskId)
                    .userId(userId)
                    .githubLink(githubLink)
                    .submissionTime(LocalDateTime.now()).build();

            return submissionRepository.save(submission);
        }

        throw new Exception("No task found with taskId "+taskId);

    }

    @Override
    public Submission getTaskSubmissionById(Long submissionId) throws Exception {
        return submissionRepository.findById(submissionId).orElseThrow(()->new Exception("No record found with submissionId "+submissionId));
    }

    @Override
    public List<Submission> getAllTaskSubmission() {
        return submissionRepository.findAll();
    }

    @Override
    public List<Submission> getTaskSubmissionByTaskId(Long taskId) {
        return submissionRepository.findByTaskId(taskId);
    }

    @Override
    public Submission acceptDeclineSubmission(Long id, String status) throws Exception {

        Submission submission=getTaskSubmissionById(id);
        submission.setStatus(status);

        if(status.equals("ACCEPT")){
        taskService.updateCompletedTask(id);}

        return submissionRepository.save(submission);
    }
}
