package com.task.task_user_service.service;

import com.task.task_user_service.config.JwtConstant;
import com.task.task_user_service.dto.ProfileDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class ExtractRoles {

    public ProfileDto extractRoles(String jwt){

        jwt=jwt.substring(7);

        SecretKey key= Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

        Claims claims= Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

        return ProfileDto.builder().userName(String.valueOf(claims.get("email")))
                .profile(String.valueOf(claims.get("authorities"))).build();
    }

}
