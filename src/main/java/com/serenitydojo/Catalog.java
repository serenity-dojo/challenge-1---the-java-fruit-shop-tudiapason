package com.serenitydojo;

//import jdk.internal.icu.text.UnicodeSet;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class Catalog {
    private static Catalog catalog = new Catalog();
    private static List<CatalogItem> availableFruits = new ArrayList<>();
    private Map<String, Double> fruitToPrice = new HashMap<>();

    public static Catalog withItems(@NotNull CatalogItem... catalogItems) {
            List<String> fruitNames = catalog.getFruitNames();
            for (CatalogItem catalogItem : catalogItems) {
                if(getFruitPosition(catalogItem.getFruit()) == -1){catalog.availableFruits.add(catalogItem);}
            }
            return catalog;
    }


    public void setPriceOf(Fruit fruit, double price) {
        try {
            int indexFruit = getFruitPosition(fruit);
            availableFruits.get(indexFruit).setPrice(price);
        }catch (IndexOutOfBoundsException noSuchFruit){
            throw new FruitUnavailableException("Requested fruit is not available");
        }
    }

    public double getPriceOf(Fruit fruit) {
        try {
            int indexFruit = getFruitPosition(fruit);
            Double fruitPrice = availableFruits.get(indexFruit).getPrice();
            if(fruitPrice == 0.00){
                throw new FruitPriceUnavailableException("Price of " + fruit.name() + " is not available");
            }
            return fruitPrice;
        }catch (IndexOutOfBoundsException noSuchFruit){
            throw new FruitUnavailableException(fruit.name() + " is not available");
        }
    }

    public List<CatalogItem> getAvailableFruits() {
        availableFruits.sort(Comparator.comparing(item -> item.getFruit().name()));
        return availableFruits;
    }

    public List<String> getFruitNames() {
        return availableFruits.stream()
                .map(catalogItem -> catalogItem.getFruit().name())
                .collect(Collectors.toList());
    }

    public static int getFruitPosition(@NotNull Fruit fruit){
        List<String> fruitNames = catalog.getFruitNames();
        return (fruitNames.indexOf(fruit.name()));
    }

    public Boolean clearCatalog(){
        availableFruits.clear();

        return (availableFruits.size() == 0) ? true : false;
    }
}
