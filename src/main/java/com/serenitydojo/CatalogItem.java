package com.serenitydojo;

public class CatalogItem {

    private final Fruit fruit;
    private Double price;

    public CatalogItem(Fruit fruit) {
        this.fruit = fruit;
        this.price = 0.00;
    }
    public CatalogItem(Fruit fruit, Double price) {
        this.fruit = fruit;
        this.price = price;
    }
    public Fruit getFruit() {
        return fruit;
    }
    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
