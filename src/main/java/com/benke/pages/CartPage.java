package com.benke.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPage extends BasePage {

    private final By cartProducts =
            By.cssSelector("#cart_info_table tbody tr");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isProductInCart(String productName) {
        waitForVisible(cartProducts);

        return driver.findElements(cartProducts)
            .stream()
            .anyMatch(row ->
                    row.findElement(By.cssSelector(".cart_description h4 a"))
                            .getText()
                            .trim()
                            .equalsIgnoreCase(productName)
            );
    }


    private WebElement getProductRow(String productName) {
        By productRow = By.xpath(
                "//table[@id='cart_info_table']//tr" +
                        "[.//td[contains(@class,'cart_description')]" +
                        "//a[normalize-space()='" + productName + "']]"
        );

        return waitForVisible(productRow);
    }

    public int getProductQuantity(String productName) {
        String quantity = getProductRow(productName)
                .findElement(By.cssSelector(".cart_quantity button"))
                .getText()
                .trim();

        return Integer.parseInt(quantity);
    }

    public String getProductPrice(String productName) {
        return getProductRow(productName)
                .findElement(By.cssSelector(".cart_price p"))
                .getText()
                .trim();
    }

    public String getProductTotal(String productName) {
        return getProductRow(productName)
                .findElement(By.cssSelector(".cart_total_price"))
                .getText()
                .trim();
    }

    public int getCartItemsCount() {
        return driver.findElements(
                By.cssSelector("#cart_info_table tbody tr")
        ).size();
    }
}