package com.example.bikerent.bikerenting.controller;

import com.example.bikerent.bikerenting.domain.Bike;
import com.example.bikerent.bikerenting.domain.User;
import com.example.bikerent.bikerenting.repository.UserRepository;
import com.example.bikerent.bikerenting.service.UserService;
import org.hibernate.type.ObjectType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<Map<String,Object>> addUser(@RequestBody User user){
        Optional<User> userDB = userRepository.findByUsername(user.getUsername());
        if(user.getEmail().isEmpty()){
            return new ResponseEntity<>(makeMap( "error","Empty email field!"), HttpStatus.FORBIDDEN);
        }

        if(user.getUsername().isEmpty()){
            return new ResponseEntity<>(makeMap( "error","Empty username field!"), HttpStatus.FORBIDDEN);
        }

        if(userDB.isPresent()){
            return new ResponseEntity<>(makeMap( "error","This username already in use!"), HttpStatus.FORBIDDEN);
        }
        if(user.getPassword().isEmpty()){
            return new ResponseEntity<>(makeMap( "error","Empty password field!"), HttpStatus.FORBIDDEN);
        }
        else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            if(user.getUsername().equals("admin")){
                user.setRoles("ROLE_ADMIN,ROLE_USER");
            }else {
                user.setRoles("ROLE_USER");
            }

            user.setActive(true);
            // user.setRoles(user.getRoles());
            userService.saveUser(user);
            return new ResponseEntity<>(makeMap( "info","User created!"), HttpStatus.CREATED);
        }

    }

    @PutMapping("/update")
    public String updateUser(Authentication authentication, @RequestBody User user){

      return "update!";
    }

    //Private route
    @GetMapping("/auth")
    public Map<String,Object> getUser(Authentication authentication){
        return userService.getUser(authentication);
    }
    @GetMapping("/all")
    public List<Map<String, Object>> allUsers(){
        return userService.allUsers();
    }


    private Map<String,Object> bikeDTO(Bike bike){
        Map<String,Object> dto = new HashMap<>();
        dto.put("id", bike.getId());
        dto.put("type", bike.getType());
        return dto;
    }
    // Get auth user
    private Optional<User> getAuthUser(Authentication authentication){
        return userRepository.findByUsername(authentication.getName());
    }

    private Map<String,Object> makeMap(String key, Object value){
        Map<String,Object> dto = new HashMap<>();
        dto.put(key,value);
        return dto;
    }
}
