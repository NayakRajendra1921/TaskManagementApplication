package com.task.task_service.controller;

import com.task.task_service.dto.ProfileDto;
import com.task.task_service.model.Task;
import com.task.task_service.model.TaskStatus;
import com.task.task_service.service.IntercommunicationService;
import com.task.task_service.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Slf4j
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private IntercommunicationService intercommunicationService;

//    @GetMapping("/getProfile")
//    public String getProfile(@RequestHeader("Authorization") String jwt) throws Exception{
//        ProfileDto user=intercommunicationService.getRoles(jwt);
//        return "successfully fetched the profile";
//    }

    @PostMapping("/getRole")
    public ResponseEntity<Task> createTask(@RequestBody Task task, @RequestHeader("Authorization") String jwt) throws Exception{

        log.info("started endpoint execution for /getRole ");
        ProfileDto user=intercommunicationService.getRoles(jwt);

        Task createdTask=taskService.createTask(task,user.getProfile());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception{

        log.info("started execution of endpoint /tasks/{id}");

        ProfileDto user=intercommunicationService.getRoles(jwt);

        Task task=taskService.getTaskById(id);

        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Task>> getAssignedUsersTask(@PathVariable Long id,
                                                     @RequestParam(required=false) TaskStatus status,
                                                     @RequestHeader("Authorization") String jwt) throws Exception{

        log.info("started endpoint execution for /user/{id}");
        ProfileDto user=intercommunicationService.getRoles(jwt);

        List<Task> tasks=taskService.assignedUsersTask(id,status);

        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    @GetMapping("/alltask")
    public ResponseEntity<List<Task>> getAllTask(@RequestParam(required=false) TaskStatus status,
                                                           @RequestHeader("Authorization") String jwt) throws Exception{

        log.info("started endpoint execution for /alltask");
        ProfileDto user=intercommunicationService.getRoles(jwt);

        List<Task> tasks=taskService.getAllTask(status);

        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    @PutMapping("/{id}/user/{userid}/assigned")
    public ResponseEntity<Task> assignTaskToUser(@PathVariable("id") Long id,
                                                 @PathVariable("userid") Long userid,
                                                 @RequestHeader("Authorization") String jwt) throws Exception{

        log.info("started endpoint execution for assignTaskToUser");
        ProfileDto user=intercommunicationService.getRoles(jwt);

        Task task=taskService.assignedToUser(userid,id);

        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @PutMapping("/{id}/user/{userid}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") Long id,
                                                 @PathVariable("userid") Long userid,
                                                 @RequestBody Task task,
                                                 @RequestHeader("Authorization") String jwt) throws Exception{

        log.info("started endpoint execution for updated task ");
        ProfileDto user=intercommunicationService.getRoles(jwt);

        Task updatedTask=taskService.updateTask(id,task,userid);

        return ResponseEntity.status(HttpStatus.CREATED).body(updatedTask);
    }

    @PutMapping("/complete/{id}")
    public ResponseEntity<Task> completeTask(@PathVariable("id") Long id) throws Exception{

        log.info("started execution for endpoint completed");

        Task updatedTask=taskService.completeTask(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(updatedTask);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") Long id) throws Exception{

        log.info("started execution for delete endpoint");

        taskService.deleteTask(id);

        return ResponseEntity.status(HttpStatus.OK).body("deleted task for id "+id);
    }


}
