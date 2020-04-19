package com.example.bikerent.bikerenting.repository;

import com.example.bikerent.bikerenting.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
}
