package com.example.bikerent.bikerenting.service;

import com.example.bikerent.bikerenting.domain.Bike;
import com.example.bikerent.bikerenting.domain.Rental;
import com.example.bikerent.bikerenting.domain.User;
import com.example.bikerent.bikerenting.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private BikeService bikeService;

    @Autowired
    private UserService userService;

    public List<Map<String,Object>> getAllRentals(){
        return rentalRepository.findAll()
                .stream()
                .map(rental -> rentalDTO(rental))
                .collect(toList());
    }

    public List<Map<String,Object>> getUserRentals(Authentication authentication){
        return userService.getUserDB(authentication).getRentals()
                .stream()
                .map(rental -> rentalDTO(rental))
                .collect(toList());
    }

    public Rental getSingleUserRental(Long id, Authentication authentication){
        User user = userService.getUserDB(authentication);
        return user.getRentals()
                .stream()
                .filter(rental1 -> rental1.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void rentBike(Long bikeId, Rental rental, Authentication authentication){
       User user =  userService.getUserDB(authentication);
       Bike bike = bikeService.getBikeById(bikeId);
       bike.setUser(user);
       user.setBike(bike);
       Rental newRental = new Rental(LocalDate.now(), rental.getRentedDays(), user, bike);
       userService.saveUser(user);
       bikeService.addBike(bike);
       rentalRepository.save(newRental);

    }

    public void renturnBike(Long rentalId, Authentication authentication){
        Rental rentalDB = rentalRepository.getOne(rentalId);
        User user =  userService.getUserDB(authentication);
        Bike bike = bikeService.getBikeById(rentalDB.getBike().getId());
        bike.setUser(null);
        user.setBike(null);
        userService.saveUser(user);
        bikeService.addBike(bike);
        rentalDB.setReturnDay(LocalDate.now());
        rentalRepository.save(rentalDB);

    }


    public Map<String,Object> rentalDTO(Rental rental) {
        Map<String,Object> dto = new HashMap<>();
        dto.put("id", rental.getId());
        dto.put("bike", bikeService.bikeDTO(rental.getBike()));
        dto.put("user", userService.userDTO(rental.getUser()));
        dto.put("rentDay", rental.getRentDay());
        dto.put("rentedDays", rental.getRentedDays());
        if(rental.getReturnDay() == null){
            dto.put("regularPrice", rental.getTotalPrice());
            dto.put("returnDay", rental.getReturnDay());
        }else {
            dto.put("returnDay", rental.getReturnDay());
            dto.put("regularPrice", rental.getTotalPrice());
            dto.put("extraDays", (int)(checkForExtraDays(rental.getRentDay(), rental.getRentedDays(), rental.getBike()) / (rental.getBike().getPricePerDay() + 3)));
            dto.put("totalPrice", rental.getTotalPrice() + checkForExtraDays(rental.getRentDay(), rental.getRentedDays(), rental.getBike()));
        }
        return dto;
    }

    private double checkForExtraDays(LocalDate date, Integer rentedDays, Bike bike){
        int extraDays = 0;

        try {
           extraDays = LocalDate.now().plusDays(6).compareTo(date.plusDays(rentedDays));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return extraDays > 0 ? extraDays *(bike.getPricePerDay() + 3) : 0.0;
    }
}
