package com.benke.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends BasePage {

    private final By cartProducts =
            By.cssSelector("#cart_info_table tbody tr");

    private final By removeProductButtons =
            By.cssSelector(".cart_quantity_delete");

    private final By emptyCartMessage =
            By.xpath("//*[contains(text(),'Cart is empty!')]");

    private By productRowLocator(String productName) {
        return By.xpath(
                "//table[@id='cart_info_table']//tr[" +
                        ".//td[contains(@class,'cart_description')]" +
                        "//a[normalize-space()='" + productName + "']]"
        );
    }

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
        return waitForVisible(productRowLocator(productName));
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

    public void removeProduct(String productName) {
        WebElement productRow = getProductRow(productName);

        productRow.findElement(removeProductButtons).click();
    }

    public boolean isProductRemoved(String productName) {
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(
                        productRowLocator(productName)
                )
        );
    }

    public boolean isCartEmpty() {
        return waitForVisible(emptyCartMessage).isDisplayed();
    }
}