package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;

import com.example.demo.entity.enums.Kind;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
@Entity
public class Animal {

    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @Column(nullable = false,updatable = false)
    private Integer age;
    @Column(nullable = false)
    private Integer population;
    @Column
    private Integer likes;
    @JsonFormat(pattern = "yyyy-dd-mm")
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdDate;
    @Column(columnDefinition = "bytea")
    private byte[] imageBytes;

    @Column
    @ElementCollection(targetClass = String.class)
    private Set<String> likedUsers = new HashSet<>();

    @Column
    @ManyToMany(targetEntity=Location.class)
    private List<Location> locations = new ArrayList<>();

    @ElementCollection(targetClass = Kind.class)
    @CollectionTable(name = "animal_kind",
            joinColumns = @JoinColumn(name = "animal_id"))
    private Set<Kind> kind = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

    public Animal(){
    }
}
