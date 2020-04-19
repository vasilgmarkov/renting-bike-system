package com.example.bikerent.bikerenting.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private LocalDate rentDay;
    private LocalDate returnDay;

    private Double totalPrice = 0.0;

    private Integer rentedDays;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bike_id")
    private Bike bike;

    public Rental(){}

    public Rental(LocalDate rentDay, Integer rentedDays, User user, Bike bike) {
        this.rentDay = rentDay;
        this.rentedDays = rentedDays;
        this.user = user;
        this.bike = bike;
        this.totalPrice = bike.getPricePerDay() * rentedDays;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getRentDay() {
        return rentDay;
    }

    public void setRentDay(LocalDate rentDay) {
        this.rentDay = rentDay;
    }

    public LocalDate getReturnDay() {
        return returnDay;
    }

    public void setReturnDay(LocalDate returnDay) {
        this.returnDay = returnDay;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getRentedDays() {
        return rentedDays;
    }

    public void setRentedDays(Integer rentedDays) {
        this.rentedDays = rentedDays;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }
}
