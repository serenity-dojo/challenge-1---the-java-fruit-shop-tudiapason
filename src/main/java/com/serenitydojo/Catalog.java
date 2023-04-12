package com.serenitydojo;

//import jdk.internal.icu.text.UnicodeSet;

import java.util.*;
import java.util.stream.Collectors;

public class Catalog {
    private static Catalog catalog = new Catalog();
    private static List<CatalogItem> availableFruits = new ArrayList<>();
    private Map<String, Double> fruitToPrice = new HashMap<>();

    public static Catalog withItems(CatalogItem... catalogItems) {
        try {
            List<String> fruitNames = catalog.getFruitNames();
            for (CatalogItem catalogItem : catalogItems) {
                if(!isAvailableFruit(catalogItem.getFruit())){catalog.availableFruits.add(catalogItem);}
            }
            return catalog;
        }catch (NullPointerException noSuchFruit){
            throw new FruitUnavailableException("Requested fruit is not an available fruit");
        }
    }


    public void setPriceOf(Fruit fruit, double price) {
        fruitToPrice.put(fruit.name(), price);
    }

    public double getPriceOf(Fruit fruitVariety) {
        try {
            return fruitToPrice.get(fruitVariety.name());
        }catch (NullPointerException noSuchFruit){
            throw new FruitPriceUnavailableException("Requested fruit price is not available");
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

    public static Boolean isAvailableFruit(Fruit fruit){
        List<String> fruitNames = catalog.getFruitNames();
        return (fruitNames.contains(fruit.name()) ? true : false);
    }

}
