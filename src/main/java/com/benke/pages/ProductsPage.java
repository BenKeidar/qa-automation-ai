package com.benke.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductsPage extends BasePage {

    private static final String URL =
            "https://automationexercise.com/products";

    private final By productsList =
            By.cssSelector(".features_items .product-image-wrapper");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openUrl(URL);
    }

    public boolean areProductsDisplayed() {
        List<WebElement> products = driver.findElements(productsList);
        return !products.isEmpty();
    }

    public int getProductsCount() {
        return driver.findElements(productsList).size();
    }
}