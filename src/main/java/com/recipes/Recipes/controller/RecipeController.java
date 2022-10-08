package com.recipes.Recipes.controller;

import com.recipes.Recipes.persistance.RecipeService;
import com.recipes.Recipes.entity.ID;
import com.recipes.Recipes.entity.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api")
@RestController
class RecipeController {

        final RecipeService recipeService;

        @Autowired
        public RecipeController(RecipeService recipeService) {
            this.recipeService = recipeService;
        }


        @GetMapping("/recipe")
        public ResponseEntity<?> getRecipe() {
            List<Recipe> recipes = recipeService.getAllRecipes();
            return new ResponseEntity<>(recipes, HttpStatus.OK);

        }

        @GetMapping("/recipe/{id}")
        public ResponseEntity<?> getRecipeById(@PathVariable Long id) {
            Optional<Recipe> recipe = recipeService.getRecipe(id);
            if (recipe.isPresent()) {
                return new ResponseEntity<>(recipe,HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

        @PostMapping("/recipe/new")
        public ResponseEntity<?> addRecipe(@Valid @RequestBody Recipe recipe) {
            Recipe newRecipe = recipeService.addRecipe(recipe);
            return new ResponseEntity<>(new ID(newRecipe),HttpStatus.OK);
        }

        @DeleteMapping()
        public ResponseEntity<?> deleteAllRecipes() {
            return new ResponseEntity<>(recipeService.deleteAllRecipes(),HttpStatus.OK);
        }

        @DeleteMapping("/recipe/{id}")
        public ResponseEntity<?> deleteRecipebyId(@PathVariable Long id) {
            try {
                recipeService.deleteRecipe(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (RuntimeException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }
}
