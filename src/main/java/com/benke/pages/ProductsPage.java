package com.benke.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsPage extends BasePage {

    private static final String URL = "https://automationexercise.com/products";

    private final By productsList = By.cssSelector(".features_items .product-image-wrapper");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openUrl(URL);
    }

    public boolean areProductsDisplayed() {
        return waitForVisible(productsList).isDisplayed();
    }

    public int getProductsCount() {
        return driver.findElements(productsList).size();
    }
}