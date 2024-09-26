package com.task.task_service.service;

import com.task.task_service.dto.ProfileDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@CacheConfig(cacheNames = "TaskService")
public class IntercommunicationService {

    @Autowired
    private  RedisTemplate redisTemplate;

    private String TASK_ROLE="/roles";

    @Autowired
    @Qualifier("taskWebClient")
    private WebClient webclient;

    @Cacheable(key = "#jwt")
    public ProfileDto getRoles(String jwt) throws Exception{

        log.info("started webclient execution");

        ProfileDto responseOutPut=null;

        if(redisTemplate.opsForValue().get(jwt)!=null)
            return (ProfileDto) redisTemplate.opsForValue().get(jwt);


        try {
            Mono<ProfileDto> taskResponse = webclient.get()
                    .uri(uriBuilder -> uriBuilder.path(TASK_ROLE).build())
                    .header("Authorization", jwt)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchangeToMono(response -> {
                                if (response.statusCode().equals(HttpStatus.OK))
                                    return response.bodyToMono(ProfileDto.class);
                                else
                                    return null;
                            }

                    );
            responseOutPut=taskResponse.block();
        }catch(Exception ex){
            throw new Exception("unable to connect");
        }

        redisTemplate.opsForValue().set(jwt,responseOutPut);

        return responseOutPut;
    }



}
