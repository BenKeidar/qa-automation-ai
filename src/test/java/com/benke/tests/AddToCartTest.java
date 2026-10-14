package com.benke.tests;

import com.benke.base.BaseTest;
import com.benke.pages.CartPage;
import com.benke.pages.HomePage;
import com.benke.pages.ProductsPage;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class AddToCartTest extends BaseTest {

    @Test
    @Description(
            "Adds Blue Top and opens the cart from the header, " +
                " and verifies quantity,unit price and total price."
    )
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


    @Test(
            description = "Verify multiple products, quantities and total prices in cart"
    )
    @Description(
            "Adds 2 units of Blue Top and 1 unit of Winter Top, " +
                    "opens the cart from the header, and verifies quantities, " +
                    "unit prices and calculated totals."
    )
    public void addMultipleProductsToCartShouldSucceed() {

        ProductsPage productsPage = new ProductsPage(driver);
        HomePage homePage = new HomePage(driver);

        Map<String, Integer> expectedQuantities = new LinkedHashMap<>();
        expectedQuantities.put("Blue Top", 2);
        expectedQuantities.put("Winter Top", 1);

        Map<String, String> expectedPrices = new LinkedHashMap<>();

        Allure.step("Open products page", productsPage::open);

        Allure.step("Search for top", () -> {
            productsPage.searchProduct("top");
        });

        Allure.step("Save product prices", () -> {
            expectedQuantities.keySet().forEach(productName -> {
                expectedPrices.put(
                        productName,
                        productsPage.getProductPrice(productName)
                );
            });
        });

        Allure.step("Add products to cart", () -> {
            expectedQuantities.forEach((productName, quantity) -> {
                Allure.step(
                        "Add " + quantity + " units of " + productName,
                        () -> productsPage.addProductMultipleTimes(
                                productName, quantity
                        )
                );
            });
        });

        CartPage cartPage = Allure.step(
                "Open cart from header",
                homePage::openCartFromHeader
        );

        expectedQuantities.forEach((productName, expectedQuantity) -> {

            Allure.step("Verify " + productName + " is in the cart", () -> {
                Assert.assertTrue(
                        cartPage.isProductInCart(productName),
                        productName + " was not found in cart"
                );
            });

            Allure.step("Verify " + productName + "'s quantity", () -> {
                Assert.assertEquals(
                        cartPage.getProductQuantity(productName),
                        expectedQuantity.intValue(),
                        "Incorrect quantity for " + productName
                );
            });

            String expectedPrice = expectedPrices.get(productName);
            Allure.step("Verify " + productName + "'s price per unit", () -> {

                Assert.assertEquals(
                        cartPage.getProductPrice(productName),
                        expectedPrice,
                        "Incorrect unit price for " + productName
                );
            });

            Allure.step("Verify " + productName + "'s total price per all units", () -> {
                int expectedTotal =
                        parsePrice(expectedPrice) * expectedQuantity;

                int actualTotal =
                        parsePrice(cartPage.getProductTotal(productName));

                Assert.assertEquals(
                        actualTotal,
                        expectedTotal,
                        "Incorrect total for " + productName
                );
            });

            Allure.step("Verify that there are only "+expectedQuantities.size() + " rows of items in the cart ", () -> {
                Assert.assertEquals(
                        cartPage.getCartItemsCount(),
                        expectedQuantities.size(),
                        "Unexpected number of products in cart"
                );
            });
        });
    }

    private int parsePrice(String price) {
        return Integer.parseInt(
                price.replaceAll("[^0-9]", "")
        );
    }


    @Test
    @Description(
            "Adds two different products to the cart, removes Blue Top, " +
                    "and verifies that only Winter Top remains."
    )
    public void removeProductFromCartShouldSucceed() {

        ProductsPage productsPage = new ProductsPage(driver);
        HomePage homePage = new HomePage(driver);

        String productToRemove = "Blue Top";
        String productToKeep = "Winter Top";

        Allure.step("Open products page", productsPage::open);

        Allure.step("Search for top", () -> {
            productsPage.searchProduct("top");
        });

        String expectedPrice = Allure.step(
                "Get product price before adding to cart",
                () -> productsPage.getProductPrice(productToKeep)
        );

        Allure.step("Add products to cart", () -> {
            productsPage.addProductMultipleTimes(productToRemove, 1);
            productsPage.addProductMultipleTimes(productToKeep, 1);
        });

        CartPage cartPage = Allure.step(
                "Open cart from header",
                homePage::openCartFromHeader
        );

        Allure.step("Verify both products are initially in cart", () -> {
            Assert.assertTrue(cartPage.isProductInCart(productToRemove));
            Assert.assertTrue(cartPage.isProductInCart(productToKeep));
        });

        Allure.step("Remove " + productToRemove, () -> {
            cartPage.removeProduct(productToRemove);
        });

        Allure.step("Verify removed product is no longer in cart", () -> {
            Assert.assertTrue(
                    cartPage.isProductRemoved(productToRemove),
                    productToRemove + " is still in cart"
            );
        });

        Allure.step("Verify remaining product is still in cart", () -> {
            Assert.assertTrue(
                    cartPage.isProductInCart(productToKeep),
                    productToKeep + " was unexpectedly removed"
            );
        });

        Allure.step("Verify cart contains exactly one product", () -> {
            Assert.assertEquals(
                    cartPage.getCartItemsCount(),
                    1,
                    "Unexpected number of products in cart"
            );
        });

        Allure.step("Verify product total price is right", () -> {
            Assert.assertEquals(
                    cartPage.getProductTotal(productToKeep),
                    expectedPrice,
                    "Incorrect total for quantity 1"
            );
        });
    }

    @Test
    @Description(
            "Adds a single product to the cart, removes it, " +
                    "and verifies that the cart is empty."
    )
    public void removingLastProductShouldEmptyCart() {

        ProductsPage productsPage = new ProductsPage(driver);
        HomePage homePage = new HomePage(driver);

        String productName = "Blue Top";

        Allure.step("Open products page", productsPage::open);

        Allure.step("Search for " + productName, () -> {
            productsPage.searchProduct(productName);
        });

        Allure.step("Add product to cart", () -> {
            productsPage.addProductMultipleTimes(productName, 1);
        });

        CartPage cartPage = Allure.step(
                "Open cart from header",
                homePage::openCartFromHeader
        );

        Allure.step("Verify product is in cart", () -> {
            Assert.assertTrue(
                    cartPage.isProductInCart(productName),
                    productName + " was not found in cart"
            );
        });

        Allure.step("Remove " + productName, () -> {
            cartPage.removeProduct(productName);
        });

        Allure.step("Verify cart is empty", () -> {
            Assert.assertTrue(
                    cartPage.isCartEmpty(),
                    "Empty cart message was not displayed"
            );

            Assert.assertEquals(
                    cartPage.getCartItemsCount(),
                    0,
                    "Cart should contain no products"
            );
        });
    }
}