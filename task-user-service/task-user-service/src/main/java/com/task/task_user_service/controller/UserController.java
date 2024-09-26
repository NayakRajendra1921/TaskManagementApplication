package com.task.task_user_service.controller;


import com.task.task_user_service.config.JwtProvider;
import com.task.task_user_service.dto.AuthResponse;
import com.task.task_user_service.dto.LoginRequest;
import com.task.task_user_service.model.User;
import com.task.task_user_service.repo.UserRepository;
import com.task.task_user_service.service.CustomUserServiceImplementation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Slf4j
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserServiceImplementation customUserDetails;

//userexception
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler
            (@RequestBody User user) throws Exception{

        String email=user.getEmail();
        String password=passwordEncoder.encode(user.getPassword());
        String fullName=user.getPassword();
        String role=user.getRole();

        Optional<User> isEmailExist=userRepository.findByEmail(email);

        if(isEmailExist.isPresent()){
            throw new Exception("User already present in database");
        }

        User newUser=User.builder()
                .email(email)
                .password(password)
                .fullName(fullName)
                .role(role).build();

        User savedUser=userRepository.save(newUser);

        Collection<GrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));

        Authentication authentication=new UsernamePasswordAuthenticationToken(email,password,authorities);

        String token= JwtProvider.generateToken(authentication);

        AuthResponse authResponse=AuthResponse.builder()
                .jwt(token)
                .status(true)
                .message("new user created").build();

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }


    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(
            @RequestBody LoginRequest loginRequest) throws Exception{

        log.info("started executing signin endpoint");

        String userName=loginRequest.getUserName();
        String password=loginRequest.getPassword();

        Authentication authentication=authenticate(userName,password);

        String token=JwtProvider.generateToken(authentication);

        AuthResponse authResponse=AuthResponse.builder()
                .message("signin successful")
                .jwt(token)
                .status(true).build();

        log.info("execution ended for signin endpoint");

        return new ResponseEntity<>(authResponse,HttpStatus.OK);

    }

    private Authentication authenticate(String userName,String password){
        UserDetails userDetails=customUserDetails.loadUserByUsername(userName);

        if(userDetails==null){
            throw new BadCredentialsException("user not found");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("wrong password");
        }

        return new UsernamePasswordAuthenticationToken(userName,null,userDetails.getAuthorities());
    }

    @GetMapping("/get")
    public ResponseEntity<String> get(){
        log.info("inside controller");
        return ResponseEntity.status(HttpStatus.OK).body("Hello welcome to login");
    }
}
