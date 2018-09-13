package com.example.zarkovic.testdesignpizzapro;


import java.util.List;

public class Pizza {
    String id;
    Ingredience ingredients;
    String name;
    Prices prices;
    List<Spices> spices;

    public Pizza() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Ingredience getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredience ingredients) {
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Prices getPrices() {
        return prices;
    }

    public void setPrices(Prices prices) {
        this.prices = prices;
    }

    public List<Spices> getSpices() {
        return spices;
    }

    public void setSpices(List<Spices> spices) {
        this.spices = spices;
    }
}
