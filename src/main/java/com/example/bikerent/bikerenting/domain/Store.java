package com.example.bikerent.bikerenting.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @OneToMany(mappedBy = "store", fetch = FetchType.EAGER)
    private Set<Bike> bikes = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public Set<Bike> getBikes() {
        return bikes;
    }

    public void setBikes(Bike bike) {
        this.bikes.add(bike);
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", bikes=" + bikes +
                '}';
    }
}
