package com.benke.tests;

import com.benke.base.BaseTest;
import com.benke.pages.CartPage;
import com.benke.pages.ProductsPage;
import io.qameta.allure.Allure;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddToCartTest extends BaseTest {

    @Test
    public void addProductToCartShouldSucceed() {

        ProductsPage productsPage = new ProductsPage(driver);
        String productName = "Blue Top";

        Allure.step("Open products page", productsPage::open);

        Allure.step("Search for " + productName, () -> {
            productsPage.searchProduct(productName);
        });

        String expectedPrice = Allure.step(
                "Get product price before adding to cart",
                () -> productsPage.getProductPrice(productName)
        );

        Allure.step("Add " + productName + " to cart", () -> {
            productsPage.addProductToCart(productName);
        });

        CartPage cartPage = Allure.step(
                "Open shopping cart", productsPage::viewCart
        );

        Allure.step("Verify " + productName + " is in cart", () -> {
            Assert.assertTrue(
                    cartPage.isProductInCart(productName),
                    productName + " was not found in the shopping cart"
            );
        });

        Allure.step("Verify " + productName + "'s quantity", () -> {
            Assert.assertEquals(
                    cartPage.getProductQuantity(productName),
                    1,
                    "Incorrect product quantity"
            );
        });

        Allure.step("Verify " + productName + "'s price", () -> {
            Assert.assertEquals(
                    cartPage.getProductPrice(productName),
                    expectedPrice,
                    "Product price does not match"
            );
        });

        Allure.step("Verify product total", () -> {
            Assert.assertEquals(
                    cartPage.getProductTotal(productName),
                    expectedPrice,
                    "Incorrect total for quantity 1"
            );
        });
    }
}