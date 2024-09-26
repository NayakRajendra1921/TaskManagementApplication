package com.task.task_service.model;

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
