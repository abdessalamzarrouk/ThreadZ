package com.threadzy.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threadzy.app.models.UserEntity;
import com.threadzy.app.services.UserEntityService;

@RestController
@CrossOrigin("http://localhost:5173")
public class UserController {
    @Autowired
    private UserEntityService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getAllUsers() throws Exception {
        List<UserEntity> users = userService.loadAllUserDetails();
        return ResponseEntity.ok(users); 
    }


    @PostMapping("/login")
    public ResponseEntity<String> loginUserRequest(@RequestParam String username,@RequestParam String password){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
            return ResponseEntity.ok("User " + username + " authenticated successfully \n");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error authenticating " + username + " caused by : " + e.getMessage() + "\n");
        }
        

    }

    @GetMapping("/register")
    public String showRegisterForm(){
        return "register";
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUserRequest(@RequestParam String username,@RequestParam String password){
        try{
            userService.registerUser(username,password);
            return ResponseEntity.ok("User " + username + " created successfully \n");
        }
        catch (Exception e){
            System.out.println("Error creating User " + username + " " + e.getMessage() + "\n");
            return ResponseEntity.badRequest().body("Error creating User " + username + "\n");
        }
    }
}
