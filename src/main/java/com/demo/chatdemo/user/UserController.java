package com.demo.chatdemo.user;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin
@RestController
public class UserController {
    private final UserRepository userRepository;

    @PostMapping("/user")
    public User signup(@RequestBody User user){
        return userRepository.save(user);
    }
    @GetMapping("/user")
    public ResponseEntity<?> getUser(User user){
        return new ResponseEntity<>(userRepository.findById(user.getUserId()), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUser(){
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

}
