package com.recipes.Recipes.persistance;

import com.recipes.Recipes.entity.Recipe;
import com.recipes.Recipes.persistance.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAllRecipes() {
        return (List<Recipe>) recipeRepository.findAll();
    }

    public Optional<Recipe> getRecipe(Long id) {
        return recipeRepository.findById(id);
    }


    public Recipe addRecipe(Recipe recipe){
        return recipeRepository.save(recipe);
    }

    public String deleteAllRecipes() {
        recipeRepository.deleteAll();

        return "All recipes were deleted";
    }

    public String deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
        return "1";

    }

}
