package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.example.demo.enums.Kind;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
@Entity
@Table(name = "animal")
public class Animal {

    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    private Long animal_id;
    @Column(unique = true)
    private String name;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false,updatable = false)
    private Integer age;
    @Column(nullable = false)
    private Integer population;
    @Column
    private Integer likes;
    @JsonFormat(pattern = "yyyy-dd-mm")
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdDate;
    @Column(nullable = false)
    private String imageLink;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "animal_location",
            joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    private Set<Location> locations = new HashSet<>();
    @Column
    @ElementCollection(targetClass = String.class)
    private Set<String> likedUsers = new HashSet<>();
    @ElementCollection(targetClass = Kind.class)
    @CollectionTable(name = "animal_kind", joinColumns = @JoinColumn(referencedColumnName = "animal_id"))
    private Set<Kind> kind = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

    public Animal(){
    }
}
