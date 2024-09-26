package com.task.task_submission_service.dto;

public enum TaskStatus {

    PENDING("PENDING"),

    ASSIGNED("ASSIGNED"),

    DONE("DONE");

    private String str;

    TaskStatus(String str){
        this.str=str;
    }

    public String getValue(){
        return str;
    }

}
