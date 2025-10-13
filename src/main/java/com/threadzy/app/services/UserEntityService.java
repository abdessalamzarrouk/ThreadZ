package com.threadzy.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.threadzy.app.models.UserEntity;
import com.threadzy.app.repositories.UserRepository;

@Service
public class UserEntityService implements UserDetailsService{
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserEntityService(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + " " + "Not Found !\n"));
        return user;
        }

    public void registerUser(String username, String password) throws Exception {
        if(userRepository.findByUsername(username).isPresent()){
            throw new Exception("Username " + username + " already exists \n");
        }
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPasswordhash(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}