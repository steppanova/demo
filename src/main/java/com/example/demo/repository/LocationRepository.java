package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Animal;
import com.example.demo.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Animal> findAllByAnimal(Animal animal);

    Optional<Animal> findLocationById(Long id);
}
