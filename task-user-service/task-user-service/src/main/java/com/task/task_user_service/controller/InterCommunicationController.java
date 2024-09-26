package com.task.task_user_service.controller;

import com.task.task_user_service.dto.ProfileDto;

import com.task.task_user_service.service.ExtractRoles;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/v1")
@Slf4j
public class InterCommunicationController {

    @Autowired
    private ExtractRoles extractRoles;

    @GetMapping("/roles")
    public ResponseEntity<ProfileDto> getRoles(@RequestHeader("Authorization") String jwt) throws BadRequestException{
        log.info("started /roles endpoint");

        if(jwt==null)
            throw new BadRequestException("invalid jwt token");

        ProfileDto body= extractRoles.extractRoles(jwt);

        log.info(body.getUserName()+" "+body.getProfile());

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

}

