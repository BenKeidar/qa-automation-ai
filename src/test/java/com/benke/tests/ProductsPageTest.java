package com.benke.tests;

import com.benke.base.BaseTest;
import com.benke.pages.ProductsPage;
import io.qameta.allure.Allure;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductsPageTest extends BaseTest {

    @Test
    public void verifyProductsAreDisplayed() {

        ProductsPage productsPage = new ProductsPage(driver);

        Allure.step("Open products page", () -> {
            productsPage.open();
        });

        Allure.step("Verify products are displayed", () -> {
            Assert.assertTrue(
                    productsPage.areProductsDisplayed(),
                    "Expected products to be displayed"
            );
        });
    }
}