package com.example.bikerent.bikerenting.repository;

import com.example.bikerent.bikerenting.domain.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Long> {
}
