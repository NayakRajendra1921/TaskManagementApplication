package com.task.task_submission_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class TaskService {

    @Autowired
    @Qualifier("taskServiceWebClient")
    private WebClient webClient;

    /*
    calling /task/{id} endpoint of task service
     */

    public Task getTaskById(String jwt,Long taskId) throws Exception{
        Task taskOutPut=null;
        try {
            Mono<Task> task = webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/tasks/{id}").build(taskId))
                    .header("Authorization", jwt)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchangeToMono(response -> {
                        if (response.statusCode().equals(HttpStatus.OK)) {
                            return response.bodyToMono(Task.class);
                        } else {
                            return null;
                        }
                    });
            taskOutPut=task.block();
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
        return taskOutPut;
    }



    /*
      calling /complete/{id} endpoint of task service
    */

    public Task updateCompletedTask(Long id) throws Exception{

        Task taskOutPut=null;
        try{
            Mono<Task> task=webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/complete/{id}").build(id))
                    .accept(MediaType.APPLICATION_JSON)
                    .exchangeToMono(response->{
                        if(response.statusCode().equals((HttpStatus.CREATED)))
                            return response.bodyToMono(Task.class);
                        else
                            return null;
                    });
            taskOutPut=task.block();
        }catch(Exception ex){
            throw new Exception("Exception occured while calling Task Service");
        }

        return taskOutPut;
    }

}
