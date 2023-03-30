package com.serenitydojo.fruitmarket;

import com.serenitydojo.Catalog;
import com.serenitydojo.CatalogItem;
import org.junit.Test;

import java.util.List;

import static com.serenitydojo.Fruit.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CatalogTest {
    Catalog catalog = Catalog.withItems(

            new CatalogItem(Pear),
            new CatalogItem(Apple),
            new CatalogItem(Banana)
    );
    @Test
    public void shouldListAvailableFruitsAlphabetically() {
        List<CatalogItem> availableFruits = catalog.getAvailableFruits();

        assertThat(availableFruits.get(0).getFruit()).isEqualTo(Apple);
        assertThat(availableFruits.get(1).getFruit()).isEqualTo(Banana);
        assertThat(availableFruits.get(2).getFruit()).isEqualTo(Pear);
    }

    @Test
    public void shouldBeAbleToUpdateTheCurrentPriceOfAFruit() {
        catalog.setPriceOf(Apple, 4.00);

        assertThat(catalog.getPriceOf(Apple)).isEqualTo(4.00);
    }
}
