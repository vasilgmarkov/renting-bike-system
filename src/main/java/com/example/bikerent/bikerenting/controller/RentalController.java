package com.example.bikerent.bikerenting.controller;

import com.example.bikerent.bikerenting.domain.Rental;
import com.example.bikerent.bikerenting.repository.RentalRepository;
import com.example.bikerent.bikerenting.service.BikeService;
import com.example.bikerent.bikerenting.service.RentalService;
import com.example.bikerent.bikerenting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/rental")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    //get all rents ADMIN
    @GetMapping("/all")
    public List<Map<String,Object>> getAllRentals(){
        return rentalService.getAllRentals();
    }

    //get rent by user USER
    @GetMapping("/byUser")
    public List<Map<String,Object>> getUserRentals(Authentication authentication){
        return rentalService.getUserRentals(authentication);
    }
    //get single rent in user by id USER
    @GetMapping("/singleUserRental/{rentalId}")
    public Map<String,Object> getSingleUserRental(@PathVariable Long rentalId, Authentication authentication){
        return rentalService.rentalDTO(rentalService.getSingleUserRental(rentalId, authentication));
    }
    //rent bike USER
    @PostMapping("/rentBike/{bikeId}")
    public String rentBike(@PathVariable Long bikeId, @RequestBody Rental rental, Authentication authentication){
        rentalService.rentBike(bikeId, rental, authentication);
        return "rented";
    }

    //return bike USER
    @PostMapping("/returnBike/{rentId}")
    public String returnBike(@PathVariable Long rentId, Authentication authentication){
        rentalService.renturnBike(rentId, authentication);
        return "returned";
    }

    //update rent ADMIN

    //delete rent ADMIN

}
