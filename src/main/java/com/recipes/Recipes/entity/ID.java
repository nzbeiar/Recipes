package com.recipes.Recipes.entity;

import org.springframework.stereotype.Component;

@Component
public class ID {

    private final Recipe recipe;

    public ID(Recipe recipe) {
        this.recipe = recipe;
    }

    public Long getid() {
        return this.recipe.getId();
    }
}
