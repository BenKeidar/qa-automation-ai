package com.benke.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By productsTitle =
            By.cssSelector("[data-test='title']");

    private final By backpackAddToCartButton =
            By.id("add-to-cart-sauce-labs-backpack");

    private final By shoppingCart =
            By.className("shopping_cart_link");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isProductsPageDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(productsTitle)
        ).isDisplayed();
    }

    public void addBackpackToCart() {
        wait.until(
                ExpectedConditions.elementToBeClickable(backpackAddToCartButton)
        ).click();
    }

    public CartPage openCart() {
        wait.until(
                ExpectedConditions.elementToBeClickable(shoppingCart)
        ).click();

        return new CartPage(driver);
    }
}