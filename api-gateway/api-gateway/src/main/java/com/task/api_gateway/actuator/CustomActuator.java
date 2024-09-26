package com.task.api_gateway.actuator;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Endpoint(id="appDetail")
@Component
public class CustomActuator {


    @ReadOperation
    public ResponseEntity<String> getAppInfo(){
        return ResponseEntity.status(HttpStatus.OK).body("This is Api Gateway Application , i,e. entry point of all endpoint");
    }

}
