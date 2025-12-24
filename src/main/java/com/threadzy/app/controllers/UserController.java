package com.threadzy.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threadzy.app.configs.JwtService;
import com.threadzy.app.models.UserEntity;
import com.threadzy.app.services.UserEntityService;

@RestController
@CrossOrigin("http://localhost:5173")
public class UserController {
    @Autowired
    private JwtService jwtService;
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
    public ResponseEntity<String> loginUserRequest(@RequestParam String username, @RequestParam String password) {
        try {
            // 1. Ask Spring Security to verify the credentials
            // This internally calls your UserDetailsService and compares passwords
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );

            // 2. If we reach here, authentication passed. 
            // Now fetch the user object to generate the token.
            UserEntity user = userService.findByUsername(username);

            // 3. Generate the JWT
            String token = jwtService.generateToken(user);

            // 4. Return the token to the user
            // In a real app, you'd usually return a JSON object, but for now, the raw string is fine.
            return ResponseEntity.ok(token);
        
        } catch (Exception e) {
                System.out.println("Error authenticating " + username + ": " + e.getMessage());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body("Invalid username or password\n");
        }
}

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUserRequest(@RequestParam String username,@RequestParam String email,@RequestParam String password) {
        try {
            userService.registerUser(username,password,email);
            return ResponseEntity.ok("User " + username + " created successfully \n");
        }
        catch (Exception e) {       
            System.out.println("Error creating User " + username + " " + e.getMessage() + "\n");
            return ResponseEntity.badRequest().body("Error creating User " + username + "\n");
        }
    }
}
