package com.benke.tests;

import com.benke.base.BaseTest;
import com.benke.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductsPageTest extends BaseTest {

    @Test
    public void verifyProductsAreDisplayed() {
        ProductsPage productsPage = new ProductsPage(driver);

        productsPage.open();

        Assert.assertTrue(
                productsPage.areProductsDisplayed(),
                "Expected products to be displayed on the products page"
        );

        Assert.assertTrue(
                productsPage.getProductsCount() > 0,
                "Expected at least one product"
        );
    }
}