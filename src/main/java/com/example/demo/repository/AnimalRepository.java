package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Animal;
import com.example.demo.entity.Location;
import com.example.demo.entity.enums.Kind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> findAllByLocations(Location location);

    List<Animal> findAllByKind(Kind kind);

    Optional<Animal> findAllByAge(Integer age);

    Optional<Animal> findAnimalById(Long id);

}
