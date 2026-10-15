package com.benke.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OrderItemsComponent extends BasePage {

    private final By tableLocator;

    public OrderItemsComponent(WebDriver driver, By tableLocator) {
        super(driver);
        this.tableLocator = tableLocator;
    }

    private By productRowLocator(String productName) {
        return By.xpath(
                ".//tbody/tr[" +
                        ".//td[contains(@class,'cart_description')]" +
                        "//a[normalize-space()='" + productName + "']]"
        );
    }

    public WebElement getProductRow(String productName) {
        WebElement table = waitForVisible(tableLocator);

        return table.findElement(productRowLocator(productName));
    }

    public String getProductPrice(String productName) {
        return getProductRow(productName)
                .findElement(By.cssSelector(".cart_price p"))
                .getText()
                .trim();
    }

    public int getProductQuantity(String productName) {
        String quantity = getProductRow(productName)
                .findElement(By.cssSelector(".cart_quantity button"))
                .getText()
                .trim();

        return Integer.parseInt(quantity);
    }

    public String getProductTotal(String productName) {
        return getProductRow(productName)
                .findElement(By.cssSelector(".cart_total_price"))
                .getText()
                .trim();
    }
}