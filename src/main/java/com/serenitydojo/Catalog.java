package com.serenitydojo;

//import jdk.internal.icu.text.UnicodeSet;

import java.util.*;
import java.util.stream.Collectors;

public class Catalog {
    private static List<CatalogItem> availableFruits = new ArrayList<>();
    private Map<String, Double> fruitToPrice = new HashMap<>();

    public void setPriceOf(Fruit fruit, double price) {
        fruitToPrice.put(fruit.name(), price);
    }

    public static Catalog withItems(CatalogItem... catalogItems) {
        Catalog catalog = new Catalog();
        List<String> fruitNames = catalog.getFruitNames();

        for (CatalogItem catalogItem : catalogItems) {
            if(!fruitNames.contains(catalogItem.getFruit().name())){catalog.availableFruits.add(catalogItem);}
        }

        return catalog;
    }

    public double getPriceOf(Fruit fruitVariety) {
        return fruitToPrice.getOrDefault(fruitVariety.name(),0.00);
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
}
