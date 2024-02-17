package com.example.repas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recommended-meals")
public class MealController {

    @Autowired
    private MealRepository mealRepository;

    @GetMapping
    public ResponseEntity<List<Meal>> getAllMeals() {
        List<Meal> meals = mealRepository.findAll();
        return new ResponseEntity<>(meals, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meal> getMealById(@PathVariable Long id) {
        Optional<Meal> optionalMeal = mealRepository.findById(id);
        if (optionalMeal.isPresent()) {
            return new ResponseEntity<>(optionalMeal.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Meal> createMeal(@RequestBody Meal meal) {
        Meal createdMeal = mealRepository.save(meal);
        return new ResponseEntity<>(createdMeal, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Meal> updateMeal(@PathVariable Long id, @RequestBody Meal updatedMeal) {
        Optional<Meal> optionalExistingMeal = mealRepository.findById(id);
        if (optionalExistingMeal.isPresent()) {
            Meal existingMeal = optionalExistingMeal.get();
            existingMeal.setName(updatedMeal.getName());
            existingMeal.setDescription(updatedMeal.getDescription());
            existingMeal.setPhotoUrl(updatedMeal.getPhotoUrl());
            existingMeal.setVideoUrl(updatedMeal.getVideoUrl());
            Meal savedMeal = mealRepository.save(existingMeal);
            return new ResponseEntity<>(savedMeal, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long id) {
        mealRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
