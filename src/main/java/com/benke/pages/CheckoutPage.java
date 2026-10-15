package com.benke.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    private final OrderItemsComponent orderItems;

    private final By totalAmount =
            By.xpath(
                    "//div[@id='cart_info']//tr[" +
                            ".//*[normalize-space()='Total Amount']" +
                            "]/td[last()]"
            );

    public CheckoutPage(WebDriver driver) {
        super(driver);

        orderItems = new OrderItemsComponent(
                driver,
                By.cssSelector("#cart_info table")
        );
    }

    public OrderItemsComponent getOrderItems() {
        return orderItems;
    }

    private final By addressDetailsHeading =
            By.xpath("//h2[normalize-space()='Address Details']");

    public boolean isAddressDetailsDisplayed() {
        return waitForVisible(addressDetailsHeading).isDisplayed();
    }

    public String getTotalAmount() {
        return waitForVisible(totalAmount).getText().trim();
    }
}