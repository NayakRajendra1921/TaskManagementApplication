package com.task.task_submission_service.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        if(getStatus())
            return Health.up().withDetail("Submission Application health is UP",200).build();
        else
            return Health.down().withDetail("Submission Application health is Down",504).build();
    }

    boolean getStatus(){
        return true;
    }
}
