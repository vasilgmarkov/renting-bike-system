package com.example.bikerent.bikerenting.service;

import com.example.bikerent.bikerenting.domain.Bike;
import com.example.bikerent.bikerenting.repository.BikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Service
public class BikeService {

    @Autowired
    private BikeRepository bikeRepository;

    public List<Bike> getAllBikes(){
        return bikeRepository.findAll();
    }

    public Bike getBikeById(Long id){
        return bikeRepository.findById(id).orElse(null);
    }

    public void addBike(Bike bike){
        bikeRepository.save(bike);
    }

    public void updateBike(Bike bike){
        Bike bikeDB = getBikeById(bike.getId());
        bikeDB.setType(bike.getType());
        bikeDB.setPricePerDay(bike.getPricePerDay());
        bikeRepository.save(bikeDB);

    }

    public void deleteBike(Long id){
        bikeRepository.delete(this.getBikeById(id));
    }

    public Map<String, Object> bikeDTO(Bike bike){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", bike.getId());
        dto.put("type", bike.getType());
        dto.put("pricePerDay", bike.getPricePerDay());
        dto.put("image", bike.getImage());
        dto.put("rented", bike.getRented());
        return dto;
    }


}
