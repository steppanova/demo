package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;

@Data
@Entity
public class Location {

    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, updatable = false)
    private String continent;
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "locations")
    private List<Animal> animals = new ArrayList<>();

    public Location(){

    }
}
