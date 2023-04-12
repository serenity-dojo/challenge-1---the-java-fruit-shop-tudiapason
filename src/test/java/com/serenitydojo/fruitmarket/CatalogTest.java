package com.serenitydojo.fruitmarket;

import com.serenitydojo.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.serenitydojo.Fruit.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CatalogTest {
    private Catalog catalog;
    private Map<Fruit, Double> datasetPrices = new HashMap<>();

    @Before
    public void setUp() {
        //Dataset is composed of a list of Fruits with its price in Euro
        datasetPrices.put(Apple, 0.00);
        datasetPrices.put(Orange, 5.50);
        datasetPrices.put(Banana, 6.00);
        datasetPrices.put(Pear, 4.50);

        //Initialize Catalog
        catalog = Catalog.withItems(
                new CatalogItem(Pear),
                new CatalogItem(Apple),
                new CatalogItem(Banana)
        );
        catalog.setPriceOf(Pear, datasetPrices.get(Pear));
        catalog.setPriceOf(Apple, datasetPrices.get(Apple));
        catalog.setPriceOf(Banana, datasetPrices.get(Banana));
    }

    //The Catalog should list the names of the currently available fruit in alphabetical order
    @Test
    public void shouldListAvailableFruitsAlphabetically() {
        List<CatalogItem> availableFruits = catalog.getAvailableFruits();
        assertThat(availableFruits.get(0).getFruit()).isEqualTo(Apple);
        assertThat(availableFruits.get(1).getFruit()).isEqualTo(Banana);
        assertThat(availableFruits.get(2).getFruit()).isEqualTo(Pear);
    }

    //You can update the catalog with the current market price of a fruit
    //The Catalog should report the price of a given type of fruit
    @Test
    public void shouldBeAbleToUpdateTheCurrentPriceOfAFruit() {
        Double newPrice = 4.00;
        catalog.setPriceOf(Apple, newPrice);
        assertThat(catalog.getPriceOf(Apple)).isEqualTo(newPrice);
    }

    //The Catalog should throw a FruitUnavailableException if the fruit is not currently available
    @Test(expected = FruitUnavailableException.class)
    public void shouldBeAbleToRaiseAFruitUnavailableExceptionForNotAvailableFruit() {
        catalog.withItems(new CatalogItem(null));
    }

    //You can add items to your shopping cart, which should keep a running total.
    @Test
    public void shouldBeAbleToAddItemsToMyShoppingCart(){
        //Dataset is composed of a list of Fruits with its weight in kilogrammes
        Map<Fruit, Double> datasetShopping = new HashMap<>();
        datasetShopping.put(Pear, 3.00);
        datasetShopping.put(Banana, 4.00);

        MyShoppingCart myShoppingCart = new MyShoppingCart(catalog);
        myShoppingCart.addItem(Pear, datasetShopping.get(Pear));
        myShoppingCart.addItem(Banana, datasetShopping.get(Banana));

        assertThat(myShoppingCart.getAddedFruits()).isEqualTo(datasetShopping);
    }

    //You can add items to your shopping cart, which should keep a running total.
    @Test
    public void shouldNotGetDiscountIfBuyLessThan5Kg(){
        //Dataset is composed of a list of Fruits with its weight in kilogrammes
        Map<Fruit, Double> datasetShopping = new HashMap<>();
        datasetShopping.put(Pear, 3.00);
        datasetShopping.put(Banana, 4.99);

        MyShoppingCart myShoppingCart = new MyShoppingCart(catalog);
        myShoppingCart.addItem(Pear, datasetShopping.get(Pear));
        Double pearPrice =  catalog.getPriceOf(Pear) * datasetShopping.get(Pear);
        myShoppingCart.addItem(Banana, datasetShopping.get(Banana));
        Double bananaPrice =   catalog.getPriceOf(Banana) * datasetShopping.get(Banana);
        Double expectedTotal = pearPrice + bananaPrice;

        assertThat(myShoppingCart.getTotal()).isEqualTo(expectedTotal);
    }

    //When you buy 5 kilos or more of any fruit, you get a 10% discount.
    @Test
    public void shouldGet10PercentDiscountIfBuy5Kg(){
        //Dataset is composed of a list of Fruits with its weight in kilogrammes
        Map<Fruit, Double> datasetShopping = new HashMap<>();
        datasetShopping.put(Pear, 4.99);
        datasetShopping.put(Banana, 5.00);

        MyShoppingCart myShoppingCart = new MyShoppingCart(catalog);
        myShoppingCart.addItem(Pear, datasetShopping.get(Pear));
        Double pearPrice =  catalog.getPriceOf(Pear) * datasetShopping.get(Pear);
        myShoppingCart.addItem(Banana, datasetShopping.get(Banana));
        Double bananaPrice = catalog.getPriceOf(Banana) * datasetShopping.get(Banana);
        Double bananaDiscount = catalog.getPriceOf(Banana) * datasetShopping.get(Banana) * 0.10;
        Double expectedTotal = pearPrice + bananaPrice - bananaDiscount;

        assertThat(myShoppingCart.getTotal()).isEqualTo(expectedTotal);
    }

    //When you buy 5 kilos or more of any fruit, you get a 10% discount.
    @Test
    public void shouldGet10PercentDiscountIfBuyMore5Kg(){
        Map<Fruit, Double> datasetShopping = new HashMap<>();
        datasetShopping.put(Pear, 4.99);
        datasetShopping.put(Banana, 5.01);

        MyShoppingCart myShoppingCart = new MyShoppingCart(catalog);
        myShoppingCart.addItem(Pear, datasetShopping.get(Pear));
        Double pearPrice =  catalog.getPriceOf(Pear) * datasetShopping.get(Pear);
        myShoppingCart.addItem(Banana, datasetShopping.get(Banana));
        Double bananaPrice = catalog.getPriceOf(Banana) * datasetShopping.get(Banana);
        Double bananaDiscount = catalog.getPriceOf(Banana) * datasetShopping.get(Banana) * 0.10;
        Double expectedTotal = pearPrice + bananaPrice - bananaDiscount;

        assertThat(myShoppingCart.getTotal()).isEqualTo(expectedTotal);
    }

    //The ShoppingCart should throw a FruitUnavailableException if the fruit is not currently available
    @Test(expected = FruitUnavailableException.class)
    public void shouldBeAbleToRaiseAFruitUnavailableExceptionForNotAvailableFruitInShoppingCart() {
        Map<Fruit, Double> datasetShopping = new HashMap<>();
        datasetShopping.put(Peaches, 4.99);

        MyShoppingCart myShoppingCart = new MyShoppingCart(catalog);
        myShoppingCart.addItem(Peaches, datasetShopping.get(Peaches));
    }

    //The ShoppingCart should throw a FruitPriceUnavailableException if the fruit price is not currently available in the Catalog
    @Test(expected = FruitPriceUnavailableException.class)
    public void shouldBeAbleToRaiseAFruitPriceUnavailableExceptionForNotAvailableFruitPriceInCatalog() {
        Map<Fruit, Double> datasetShopping = new HashMap<>();
        datasetShopping.put(Rambutan, 1.00);

        catalog.withItems(new CatalogItem(Rambutan));

        MyShoppingCart myShoppingCart = new MyShoppingCart(catalog);
        myShoppingCart.addItem(Rambutan, datasetShopping.get(Rambutan));
    }
}
