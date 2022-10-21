package com.recipes.Recipes.presentation;

import com.recipes.Recipes.entity.ID;
import com.recipes.Recipes.entity.Recipe;
import com.recipes.Recipes.persistance.RecipeService;
import com.recipes.Recipes.persistance.UserService;
import com.recipes.Recipes.security.User;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RecipeController {
    final RecipeService recipeService;
    final UserService userService;

    @Autowired
    public RecipeController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }

    @GetMapping("/recipe")
    public ResponseEntity<?> getRecipes() {
        List<Recipe> foundRecipes = recipeService.getAllRecipes();
        return new ResponseEntity<>(foundRecipes,HttpStatus.OK);
    }

    @GetMapping("/recipe/{id}")
    public ResponseEntity<?> getRecipe(@PathVariable long id) {
        Recipe foundRecipe = recipeService.findRecipeById(id);
        return new ResponseEntity<>(foundRecipe,HttpStatus.OK);
    }


    @PostMapping("/recipe/new")
    public ResponseEntity<?> postRecipe(@Valid @RequestBody Recipe recipe) {
        Recipe newRecipe = recipeService.addRecipe(recipe);
        return new ResponseEntity<>(new ID(newRecipe.getId()),HttpStatus.OK);
    }

    @PutMapping("/recipe/{id}")
    public ResponseEntity<?> postRecipe(@Valid @RequestBody Recipe recipe, @PathVariable Long id, @AuthenticationPrincipal UserDetails details) {
        Recipe updatedRecipe = recipeService.updateRecipebyId(recipe,id);
        if (updatedRecipe != null) {
            return new ResponseEntity<>(updatedRecipe,HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/recipe/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable long id) {
        try {
            Recipe recipe = recipeService.deleteRecipeById(id);
            if (recipe != null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/recipe")
    public ResponseEntity<?> deleteAllRecipes() {
        try {
            recipeService.deleteAllRecipes();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/recipe/search")
    public ResponseEntity<?> getRecipeByCategory(@Valid @RequestParam(required = false) String category, @RequestParam(required = false) String name) {
        if ( category != null && name == null){
            List<Recipe> found = recipeService.getRecipesByCategory(category);
            return new ResponseEntity<>(found,HttpStatus.OK);
        } else if (category == null && name != null) {
            List<Recipe> found = recipeService.getRecipesByName(name);
            return new ResponseEntity<>(found,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid User user) {
        // input validation omitted for brevity
        if (userService.findUserByEmail(user.getEmail()) == null) {
            user.setPassword(encoder.encode(user.getPassword()));
            userService.addUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/users")
    public ResponseEntity<?> users() {
        List<User> foundUsers = userService.getAllUsers();
        return new ResponseEntity<>(foundUsers,HttpStatus.OK);
    }


    @GetMapping("/getUser")
    public User getUser(@RequestParam String email) {
        return userService.findUserByEmail(email);
    }


}
