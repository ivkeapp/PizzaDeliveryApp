package com.example.zarkovic.testdesignpizzapro;

import java.util.ArrayList;

public class Pizzas {

    ArrayList<Pizza> p;

    public Pizzas() {
    }

    public Pizzas(ArrayList<Pizza> p) {
        this.p = p;
    }

    public ArrayList<Pizza> getP() {
        return p;
    }

    public void setP(ArrayList<Pizza> p) {
        this.p = p;
    }
}
