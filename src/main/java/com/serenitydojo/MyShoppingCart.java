package com.serenitydojo;

import java.util.*;
import java.util.stream.Collectors;

public class MyShoppingCart {
     private Map<Fruit, Double> addedFruits = new HashMap<>();
    private double totalShoppingCart;
    private Catalog catalog;

    public MyShoppingCart(Catalog catalog){
        totalShoppingCart = 0.00;
        this.catalog = catalog;
    }

    public void addItem(Fruit fruit, Double weight) {
        Double finalFruitPrice = catalog.getPriceOf(fruit) * weight;
        Double discountValue = (weight >= 5.00) ? 0.9 : 1.0;

        addedFruits.put(fruit, weight);
        totalShoppingCart = totalShoppingCart + finalFruitPrice * discountValue;
    }

    public Map<Fruit, Double> getAddedFruits() {
        return addedFruits;
    }

    public double getTotal() {
        return totalShoppingCart;
    }
}
