package com.recipes.Recipes.persistance;

import com.recipes.Recipes.entity.Recipe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    @Query(
            value = "SELECT * FROM test_mv_ r ORDER BY r.date DESC",
            nativeQuery = true)
    List<Recipe> getRecipesSortedByDate();

}
