package com.workintech.fswebs17d1.controller;

import com.workintech.fswebs17d1.entity.Animal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/workintech")
public class AnimalController {

    @Value("${course.name}")
    private String courseName;
    @Value("${project.developer.fullname}")
    private String developerFullName;

    private Map<Integer, Animal> animals = new HashMap<>();

    public AnimalController() {
        animals.put(1, new Animal(1, "Cat"));
        animals.put(2, new Animal(2, "Dog"));
        animals.put(3, new Animal(3, "Bird"));
    }

    @GetMapping("/animal")
    public List<Animal> getAllAnimals() {
        return new ArrayList<>(animals.values());
    }

    @GetMapping("/animal/{id}")
    public ResponseEntity<Animal> getAnimalById(@PathVariable Integer id) {
        Animal animal = animals.get(id);
        if (animal.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/animal")
    public ResponseEntity<Animal> createAnimal(@RequestBody Animal animal) {
        if (animal.getId() == null || animal.getName() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        animals.put(animal.getId(), animal);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/animal/{id}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable Integer id, @RequestBody Animal animalFromBody) {
        Animal existing = animals.get(id);
        if (existing == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Integer newId = animalFromBody.getId() != null ? animalFromBody.getId() : id;

        animals.remove(id);
        Animal updated = new Animal(newId, animalFromBody.getName());
        animals.put(newId, updated);

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }


    @DeleteMapping("/animal/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Integer id) {
        Animal removed = animals.remove(id);
        if (removed == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
