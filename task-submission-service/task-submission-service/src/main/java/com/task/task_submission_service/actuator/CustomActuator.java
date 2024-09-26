package com.task.task_submission_service.actuator;


import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Endpoint(id = "appDetail")
@Component
public class CustomActuator {

    @ReadOperation
    public String giveAppDetail(){

        return "This is Task Submission Microservice";
    }

}
