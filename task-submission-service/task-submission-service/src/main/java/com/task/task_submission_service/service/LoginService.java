package com.task.task_submission_service.service;

import com.task.task_submission_service.dto.ProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@CacheConfig(cacheNames = "SubmissionService")
public class LoginService {

    @Autowired
    @Qualifier("loginServiceWebClient")
    private WebClient webClient;

    @Autowired
    @Qualifier("loginRedisTemplate")
    private RedisTemplate redisTemplate;

    /*
      calling login service to get profiles
    */

    public ProfileDto getProfile(String jwt){
        ProfileDto profileOutPut=null;

        if(redisTemplate.opsForValue().get(jwt)!=null)
            return (ProfileDto) redisTemplate.opsForValue().get(jwt);

        Mono<ProfileDto> profile=webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/roles").build())
                .header("Authorization",jwt)
                .exchangeToMono(response->{
                    if(response.statusCode().equals(HttpStatus.OK))
                        return response.bodyToMono(ProfileDto.class);
                    else
                        return null;
                });

        profileOutPut=profile.block();

        redisTemplate.opsForValue().set(jwt,profileOutPut);

        return profileOutPut;
    }
}
