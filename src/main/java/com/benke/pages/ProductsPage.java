package com.benke.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductsPage extends BasePage {

    private static final String URL = "https://automationexercise.com/products";

    private final By productsList = By.cssSelector(".features_items .product-image-wrapper");

    private final By searchInput = By.id("search_product");

    private final By searchButton = By.id("submit_search");

    private final By searchedProductsHeading =
            By.xpath("//h2[normalize-space()='Searched Products']");

    private final By productNames =
            By.cssSelector(".features_items .productinfo p");

    private final By viewCartLink =
            By.cssSelector("#cartModal a[href='/view_cart']");

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

    public void searchProduct(String productName) {
        type(searchInput, productName);
        click(searchButton);
    }

    public boolean isSearchedProductsDisplayed() {
        return waitForVisible(searchedProductsHeading)
                .isDisplayed();
    }

    public int getSearchResultsCount() {
        waitForVisible(searchedProductsHeading);
        return driver.findElements(productNames).size();
    }

    public boolean doesAnyResultContain(String keyword) {
        waitForVisible(searchedProductsHeading);

        String expected = keyword.trim().toLowerCase();

        return driver.findElements(productNames)
                .stream()
                .map(element -> element.getText().trim().toLowerCase())
                .anyMatch(name -> name.contains(expected));
    }


    public void addProductToCart(String productName) {
        By addToCartButton = By.xpath(
                "//div[contains(@class,'productinfo')]" +
                        "[.//p[normalize-space()='" + productName + "']]" +
                        "//a[contains(@class,'add-to-cart')]"
        );

        WebElement button = waitForClickable(addToCartButton);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                button
        );

        button.click();
    }

    public String getProductPrice(String productName) {
        By productPrice = By.xpath(
                "//div[contains(@class,'productinfo')]" +
                        "[.//p[normalize-space()='" + productName + "']]" +
                        "//h2"
        );

        return waitForVisible(productPrice).getText().trim();
    }

    public CartPage viewCart() {
        click(viewCartLink);
        return new CartPage(driver);
    }
}