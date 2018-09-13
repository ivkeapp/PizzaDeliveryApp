package com.example.zarkovic.testdesignpizzapro;


public class Ingredience {
    Meet meat;
    Cheese cheese;
    Dough dough;
    Mushrooms mushrooms;
    Pelat pelat;

    public Ingredience() {
    }

    public Ingredience(Meet meat, Cheese cheese, Dough dough, Mushrooms mushrooms, Pelat pelat) {
        this.meat = meat;
        this.cheese = cheese;
        this.dough = dough;
        this.mushrooms = mushrooms;
        this.pelat = pelat;
    }

    public Meet getMeat() {
        return meat;
    }

    public void setMeat(Meet meat) {
        this.meat = meat;
    }

    public Cheese getCheese() {
        return cheese;
    }

    public void setCheese(Cheese cheese) {
        this.cheese = cheese;
    }

    public Dough getDough() {
        return dough;
    }

    public void setDough(Dough dough) {
        this.dough = dough;
    }

    public Mushrooms getMushrooms() {
        return mushrooms;
    }

    public void setMushrooms(Mushrooms mushrooms) {
        this.mushrooms = mushrooms;
    }

    public Pelat getPelat() {
        return pelat;
    }

    public void setPelat(Pelat pelat) {
        this.pelat = pelat;
    }
}
