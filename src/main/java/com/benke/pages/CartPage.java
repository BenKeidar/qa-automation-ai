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

    private final By proceedToCheckoutButton =
            By.cssSelector("a.check_out");

    private By productRowLocator(String productName) {
        return By.xpath(
                "//table[@id='cart_info_table']//tr[" +
                        ".//td[contains(@class,'cart_description')]" +
                        "//a[normalize-space()='" + productName + "']]"
        );
    }

    private final By checkoutLoginPrompt =
            By.xpath(
                    "//*[@id='checkoutModal']" +
                            "//*[contains(text(),'Register / Login account')]"
            );

    private final By cartProductRows =
            By.cssSelector("#cart_info_table tbody tr[id^='product-']");

    private final OrderItemsComponent orderItems;

    public CartPage(WebDriver driver) {
        super(driver);

        orderItems = new OrderItemsComponent(
                driver,
                By.id("cart_info_table")
        );
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
        return orderItems.getProductQuantity(productName);
    }

    public String getProductPrice(String productName) {
        return orderItems.getProductPrice(productName);
    }

    public String getProductTotal(String productName) {
        return orderItems.getProductTotal(productName);
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

    public CheckoutPage proceedToCheckout() {
        click(proceedToCheckoutButton);
        return new CheckoutPage(driver);
    }

    public boolean isLoginRequiredForCheckout() {
        return waitForVisible(checkoutLoginPrompt).isDisplayed();
    }

    public void clearCart() {

        while (!driver.findElements(cartProductRows).isEmpty()) {

            WebElement productRow =
                    driver.findElements(cartProductRows).get(0);

            WebElement removeButton =
                    productRow.findElement(
                            By.cssSelector(".cart_quantity_delete")
                    );

            removeButton.click();

            wait.until(
                    ExpectedConditions.stalenessOf(productRow)
            );
        }
    }
}