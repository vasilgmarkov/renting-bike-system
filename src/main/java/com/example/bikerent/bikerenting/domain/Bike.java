package com.example.bikerent.bikerenting.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String type;
    private Double pricePerDay;
    private Boolean rented = false;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToOne(mappedBy = "bike")
    private User user;

    @OneToMany(mappedBy = "bike", fetch = FetchType.EAGER)
    private Set<Rental> rentals = new HashSet<>();

    public Bike () {}

    public Bike(String type, Double pricePerDay, String image) {
        this.type = type;
        this.pricePerDay = pricePerDay;
        this.image = image;
    }

    public Boolean getRented() {
        return rented;
    }

    public void setRented(Boolean rented) {
        this.rented = rented;
    }

    public Set<Rental> getRentals() {
        return rentals;
    }

    public void addRental(Rental rental) {
        this.rentals.add(rental);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.rented = this.rented ? false : true;
        this.user = user;
    }



    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
        store.setBikes(this);
    }



    @Override
    public String toString() {
        return "Bike{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", pricePerDay=" + pricePerDay +
                '}';
    }
}
