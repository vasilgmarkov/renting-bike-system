package com.example.bikerent.bikerenting.controller;

import com.example.bikerent.bikerenting.domain.Bike;
import com.example.bikerent.bikerenting.service.BikeService;
import org.hibernate.type.ObjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bikes")
public class BikeController {

    @Autowired
    private BikeService bikeService;

    @GetMapping("/all")
    public List<Map<String, Object>> getAllBikes(){
        return bikeService.getAllBikes()
                .stream()
                .map(bike -> bikeService.bikeDTO(bike))
                .collect(Collectors.toList());
    }

    @GetMapping("/byId/{id}")
    public Map<String, Object> getBikeById(@PathVariable Long id){
        if(bikeService.getBikeById(id)!=null){
            Bike bike =  bikeService.getBikeById(id);
            return bikeService.bikeDTO(bike);
        }else{
            return makeInfo("error", "No bike with id: "+ id);
        }

    }

    @PutMapping("/update")
    public void updateBike(@RequestBody Bike bike){
         bikeService.updateBike(bike);
    }



    private Map<String, Object> makeInfo(String type, Object object){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put(type, object);
        return dto;
    }


}
