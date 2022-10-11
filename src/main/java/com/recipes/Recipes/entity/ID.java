package com.recipes.Recipes.entity;

import lombok.Data;

@Data
public class ID {
    private Long id;

    public ID(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

