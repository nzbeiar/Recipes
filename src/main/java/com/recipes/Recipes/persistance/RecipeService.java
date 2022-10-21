package com.recipes.Recipes.persistance;

import com.recipes.Recipes.entity.Recipe;
import com.recipes.Recipes.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Recipe addRecipe(Recipe recipe) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByEmail(email);
        if (user != null) {
            recipe.setUser(user);
            return recipeRepository.save(recipe);
        } else {
            return null;
        }

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
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByEmail(email);

        Recipe found = recipeRepository.findById(id).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Recipe not found for id = " + id));
        if (found.getUser().getId().equals(user.getId())) {
            found.setName(recipe.getName());
            found.setDate(LocalDateTime.now());
            found.setCategory(recipe.getCategory());
            found.setDirections(recipe.getDirections());
            found.setDescription(recipe.getDescription());
            found.setIngredients(recipe.getIngredients());
            recipeRepository.save(found);
            return found;
        } else {
            return null;
        }

    }

    @Transactional
    public Recipe deleteRecipeById(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByEmail(email);
        Recipe recipe = findRecipeById(id);
        if (recipe.getUser().getId().equals(user.getId())) {
            recipeRepository.delete(recipe);
            return recipe;
        } else {
            return null;
        }
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