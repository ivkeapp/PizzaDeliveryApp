package com.example.zarkovic.testdesignpizzapro;

public class Pizza {

    String id, name, size, cheese, dough, meet, vegetables, topping, price;

    public Pizza(String id, String name, String size, String cheese, String dough,
                 String meet, String vegetables, String topping, String price) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.cheese = cheese;
        this.dough = dough;
        this.meet = meet;
        this.vegetables = vegetables;
        this.topping = topping;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCheese() {
        return cheese;
    }

    public void setCheese(String cheese) {
        this.cheese = cheese;
    }

    public String getDough() {
        return dough;
    }

    public void setDough(String dough) {
        this.dough = dough;
    }

    public String getMeet() {
        return meet;
    }

    public void setMeet(String meet) {
        this.meet = meet;
    }

    public String getVegetables() {
        return vegetables;
    }

    public void setVegetables(String vegetables) {
        this.vegetables = vegetables;
    }

    public String getTopping() {
        return topping;
    }

    public void setTopping(String topping) {
        this.topping = topping;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
