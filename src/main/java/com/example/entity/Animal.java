package com.example.entity;

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

import com.example.entity.enums.Kind;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
@Entity
@Table(name = "animal")
public class Animal {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, updatable = true)
    private String name;
    @Column(nullable = false)
    private String location;
    @Column(updatable = false)
    private Integer age;
    @Column(updatable = true)
    private Integer population;
    @Column(updatable = true)
    private Integer likes;
    @JsonFormat(pattern = "yyyy-dd-mm")
    @Column(updatable = false)
    private LocalDateTime createdDate;

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
