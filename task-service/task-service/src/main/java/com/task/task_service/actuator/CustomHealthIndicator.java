package com.task.task_service.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        if(check()){
            return Health.up().withDetail("Application is UP",200).build();
        }
        return Health.down().withDetail("Application is DOWN",500).build();
    }

    public boolean check(){
        return true;
    }

}
