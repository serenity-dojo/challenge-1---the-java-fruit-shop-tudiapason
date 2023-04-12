package com.serenitydojo.fruitmarket;

import com.serenitydojo.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.serenitydojo.Fruit.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CatalogTest {
    private static Catalog catalog;
    private static Map<Fruit, Double> datasetPrices;

    @BeforeClass
    public static void beforeClass() {
        //Dataset is composed of a list of Fruits with its price in Euro
        datasetPrices = new HashMap<>();
        datasetPrices.put(Apple, 4.00);
        datasetPrices.put(Orange, 5.50);
        datasetPrices.put(Banana, 6.00);
        datasetPrices.put(Pear, 4.50);

        catalog = new Catalog();
    }

    @Before
    public void beforeMethod() {
        assertThat(catalog.clearCatalog()).isEqualTo(true);
    }

    //You can add items without price to the Catalog .
    @Test
    public void shouldBeAbleToAddACatalogItemWithoutPrice() {
        catalog.withItems(new CatalogItem(Orange));

        assertThat(catalog.getAvailableFruits().get(0).getFruit()).isEqualTo(Orange);
        assertThat(catalog.getAvailableFruits().get(0).getPrice()).isEqualTo(0.00);
    }
    //You can update the catalog with the current market price of a fruit
    //The Catalog should report the price of a given type of fruit
    @Test
    public void shouldBeAbleToUpdateTheCurrentPriceOfAFruit() {
        catalog.withItems(new CatalogItem(Apple));
        catalog.setPriceOf(Apple, datasetPrices.get(Apple));
        assertThat(catalog.getAvailableFruits().get(0).getPrice()).isEqualTo(datasetPrices.get(Apple));
    }

    //You can add items with price to the Catalog .
    @Test
    public void shouldBeAbleToAddACatalogItemWithPrice() {
        catalog.withItems(new CatalogItem(Pear,  datasetPrices.get(Pear)));
        assertThat(catalog.getAvailableFruits().get(0).getFruit()).isEqualTo(Pear);
        assertThat(catalog.getAvailableFruits().get(0).getPrice()).isEqualTo(datasetPrices.get(Pear));
    }
    //The Catalog should list the names of the currently available fruit in alphabetical order
    @Test
    public void shouldListAvailableFruitsAlphabetically() {
        catalog.withItems(
                new CatalogItem(Pear),
                new CatalogItem(Apple),
                new CatalogItem(Banana)
        );

        assertThat(catalog.getAvailableFruits().get(0).getFruit()).isEqualTo(Apple);
        assertThat(catalog.getAvailableFruits().get(1).getFruit()).isEqualTo(Banana);
        assertThat(catalog.getAvailableFruits().get(2).getFruit()).isEqualTo(Pear);
    }

    //The Catalog should throw a FruitUnavailableException if the fruit is not currently available
    @Test (expected = FruitUnavailableException.class)
    public void shouldBeAbleToRaiseAFruitUnavailableExceptionForUpdatingANotAvailableFruit() {
        catalog.withItems(new CatalogItem(Pear));
        catalog.setPriceOf(Apple, datasetPrices.get(Apple));
    }

    //You can add items to your shopping cart, which should keep a running total.
    @Test
    public void shouldBeAbleToAddItemsToMyShoppingCart(){
        catalog.withItems(
                new CatalogItem(Apple, datasetPrices.get(Apple)),
                new CatalogItem(Orange, datasetPrices.get(Orange)),
                new CatalogItem(Banana, datasetPrices.get(Banana)),
                new CatalogItem(Pear, datasetPrices.get(Pear))
        );

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
        catalog.withItems(
                new CatalogItem(Apple, datasetPrices.get(Apple)),
                new CatalogItem(Orange, datasetPrices.get(Orange)),
                new CatalogItem(Banana, datasetPrices.get(Banana)),
                new CatalogItem(Pear, datasetPrices.get(Pear))
        );

        //Dataset is composed of a list of Fruits with its weight in kilogrammes
        Map<Fruit, Double> datasetShopping = new HashMap<>();
        datasetShopping.put(Pear, 3.00);
        datasetShopping.put(Banana, 4.99);

        MyShoppingCart myShoppingCart = new MyShoppingCart(catalog);
        myShoppingCart.addItem(Pear, datasetShopping.get(Pear));
        myShoppingCart.addItem(Banana, datasetShopping.get(Banana));

        Double pearPrice =  catalog.getPriceOf(Pear) * datasetShopping.get(Pear);
        Double bananaPrice =   catalog.getPriceOf(Banana) * datasetShopping.get(Banana);
        Double expectedTotal = pearPrice + bananaPrice;

        assertThat(myShoppingCart.getTotal()).isEqualTo(expectedTotal);
    }

    //When you buy 5 kilos or more of any fruit, you get a 10% discount.
    @Test
    public void shouldGet10PercentDiscountIfBuy5Kg(){
        catalog.withItems(
                new CatalogItem(Apple, datasetPrices.get(Apple)),
                new CatalogItem(Orange, datasetPrices.get(Orange)),
                new CatalogItem(Banana, datasetPrices.get(Banana)),
                new CatalogItem(Pear, datasetPrices.get(Pear))
        );

        //Dataset is composed of a list of Fruits with its weight in kilogrammes
        Map<Fruit, Double> datasetShopping = new HashMap<>();
        datasetShopping.put(Pear, 4.99);
        datasetShopping.put(Banana, 5.00);

        MyShoppingCart myShoppingCart = new MyShoppingCart(catalog);
        myShoppingCart.addItem(Pear, datasetShopping.get(Pear));
        myShoppingCart.addItem(Banana, datasetShopping.get(Banana));

        Double pearPrice =  catalog.getPriceOf(Pear) * datasetShopping.get(Pear);
        Double bananaPrice = catalog.getPriceOf(Banana) * datasetShopping.get(Banana);
        Double bananaDiscount = catalog.getPriceOf(Banana) * datasetShopping.get(Banana) * 0.10;
        Double expectedTotal = pearPrice + bananaPrice - bananaDiscount;

        assertThat(myShoppingCart.getTotal()).isEqualTo(expectedTotal);
    }

    //When you buy 5 kilos or more of any fruit, you get a 10% discount.
    @Test
    public void shouldGet10PercentDiscountIfBuyMore5Kg(){
        catalog.withItems(
                new CatalogItem(Apple, datasetPrices.get(Apple)),
                new CatalogItem(Orange, datasetPrices.get(Orange)),
                new CatalogItem(Banana, datasetPrices.get(Banana)),
                new CatalogItem(Pear, datasetPrices.get(Pear))
        );

        Map<Fruit, Double> datasetShopping = new HashMap<>();
        datasetShopping.put(Pear, 4.99);
        datasetShopping.put(Banana, 5.01);

        MyShoppingCart myShoppingCart = new MyShoppingCart(catalog);
        myShoppingCart.addItem(Pear, datasetShopping.get(Pear));
        myShoppingCart.addItem(Banana, datasetShopping.get(Banana));

        Double pearPrice =  catalog.getPriceOf(Pear) * datasetShopping.get(Pear);
        Double bananaPrice = catalog.getPriceOf(Banana) * datasetShopping.get(Banana);
        Double bananaDiscount = catalog.getPriceOf(Banana) * datasetShopping.get(Banana) * 0.10;
        Double expectedTotal = pearPrice + bananaPrice - bananaDiscount;

        assertThat(myShoppingCart.getTotal()).isEqualTo(expectedTotal);
    }

    //The ShoppingCart should throw a FruitUnavailableException if the fruit is not currently available
    @Test(expected = FruitUnavailableException.class)
    public void shouldBeAbleToRaiseAFruitUnavailableExceptionForNotAvailableFruitInShoppingCart() {
        catalog.withItems(new CatalogItem(Apple, datasetPrices.get(Apple)));

        Map<Fruit, Double> datasetShopping = new HashMap<>();
        datasetShopping.put(Peaches, 4.99);

        MyShoppingCart myShoppingCart = new MyShoppingCart(catalog);
        myShoppingCart.addItem(Peaches, datasetShopping.get(Peaches));
    }

    //The ShoppingCart should throw a FruitPriceUnavailableException if the fruit price is not currently available in the Catalog
    @Test (expected = FruitPriceUnavailableException.class)
    public void shouldBeAbleToRaiseAFruitPriceUnavailableExceptionForNotAvailableFruitPriceInCatalog() {
        catalog.withItems(new CatalogItem(Apple));

        Map<Fruit, Double> datasetShopping = new HashMap<>();
        datasetShopping.put(Apple, 1.00);

        MyShoppingCart myShoppingCart = new MyShoppingCart(catalog);
        myShoppingCart.addItem(Apple, datasetShopping.get(Apple));
    }
}
