package com.example.bikerent.bikerenting.service;

import com.example.bikerent.bikerenting.domain.User;
import com.example.bikerent.bikerenting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BikeService bikeService;
    @Autowired
    private RentalService rentalService;

    public Map<String,Object> getUser(Authentication authentication){
        Map<String,Object> userDto = new HashMap<>();
        Optional<User> user = userRepository.findByUsername(authentication.getName());
        userDto.put("id", user.get().getId());
        userDto.put("name", user.get().getUsername());
        userDto.put("roles", user.get().getRoles().split(","));
        if(user.get().getBike()!=null){
            userDto.put("bike", bikeService.bikeDTO(user.get().getBike()));
        }
        userDto.put("rentals", getUserDB(authentication).getRentals()
                .stream()
                .sorted((r1,r2)-> r2.getId().compareTo(r1.getId()))
                .map(rental -> rentalService.rentalDTO(rental)));
        userDto.put("actualRent",  getUserDB(authentication).getRentals()
                .stream().filter(rental -> rental.getReturnDay()==null).findFirst().map(rental -> rentalService.rentalDTO(rental)));
        return userDto;
    }

    public User getUserDB(Authentication authentication){
                return userRepository.findByUsername(authentication.getName()).get();
    }


    public List<Map<String, Object>> allUsers(){
        return userRepository.findAll()
                .stream()
                .map(user -> userDTO(user))
                .collect(Collectors.toList());
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public Map<String,Object> userDTO(User user){
        Map<String,Object> userDto = new HashMap<>();
        userDto.put("id", user.getId());
        userDto.put("name", user.getUsername());
        userDto.put("email", user.getEmail());
        return userDto;
    }
}
