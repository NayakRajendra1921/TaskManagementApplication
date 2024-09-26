package com.task.task_service.actuator;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Endpoint(id="appDescription")
@Component
public class CustomActuator {

    @ReadOperation
    public String getDescriptionOfApplication(){
        return "This is a Task service application to create and update tasks";
    }
}
