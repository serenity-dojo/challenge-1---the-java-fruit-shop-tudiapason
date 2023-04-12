package com.serenitydojo;

import java.util.*;
import java.util.stream.Collectors;

public class MyShoppingCart {
     private Map<Fruit, Double> addedFruits = new HashMap<>();
    private double totalShoppingCart;
    private Catalog catalog;

    public MyShoppingCart(Catalog catalog){
        totalShoppingCart = 0;
        this.catalog = catalog;
    }

    public void addItem(Fruit fruit, Double weight) {
        try {
            addedFruits.put(fruit, weight);
            List<String> fruitNames = catalog.getFruitNames();
            if(!fruitNames.contains(fruit.name())){
                throw new FruitUnavailableException("Requested fruit is not an available fruit");
            }
            Double finalFruitPrice = catalog.getPriceOf(fruit) * weight;
            Double discountValue = (weight >= 5.00) ? 0.9 : 1.0;
            totalShoppingCart = totalShoppingCart + finalFruitPrice * discountValue;
        }catch (NullPointerException noSuchFruit){
            throw new FruitPriceUnavailableException("The price of " + fruit.name() + " is not available");
        }
    }

    public Map<Fruit, Double> getAddedFruits() {
        return addedFruits;
    }

    public double getTotal() {
        return totalShoppingCart;
    }
}
