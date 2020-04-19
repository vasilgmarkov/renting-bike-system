package com.example.bikerent.bikerenting.repository;

import com.example.bikerent.bikerenting.domain.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
}
