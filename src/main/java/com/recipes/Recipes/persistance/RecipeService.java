package com.recipes.Recipes.persistance;

import com.recipes.Recipes.entity.Recipe;
import com.recipes.Recipes.persistance.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Transactional
    public Recipe addRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Transactional
    public List<Recipe> getAllRecipes() {
        return (List<Recipe>) recipeRepository.findAll();
    }


    @Transactional
    public Recipe findRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Recipe not found for id = " + id));
    }

    @Transactional
    public Recipe updateRecipebyId(Recipe recipe,Long id) {
        Recipe found = recipeRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Recipe not found for id = " + id));
        found.setName(recipe.getName());
        found.setDate(LocalDateTime.now());
        found.setCategory(recipe.getCategory());
        found.setDirections(recipe.getDirections());
        found.setDescription(recipe.getDescription());
        found.setIngredients(recipe.getIngredients());
        recipeRepository.save(found);
        return found;
    }

    @Transactional
    public void deleteRecipeById(Long id) {
        Recipe recipe = findRecipeById(id);
        recipeRepository.delete(recipe);
    }

    @Transactional
    public void deleteAllRecipes() {
        recipeRepository.deleteAll();
    }

    public List<Recipe> getRecipesByName(String name) {
        List<Recipe> sortedRecipes = recipeRepository.getRecipesSortedByDate();
        return sortedRecipes
                .stream()
                .filter(recipe -> recipe
                        .getName().toLowerCase()
                        .contains(name.toLowerCase()))
                .toList();
    }

    public List<Recipe> getRecipesByCategory(String category) {
        List<Recipe> sortedRecipes = recipeRepository.getRecipesSortedByDate();
        return sortedRecipes
                .stream()
                .filter(recipe -> recipe
                        .getCategory()
                        .equalsIgnoreCase(category))
                .toList();
    }
}
