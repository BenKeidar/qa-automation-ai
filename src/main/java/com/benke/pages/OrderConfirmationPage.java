package com.benke.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderConfirmationPage extends BasePage {

    private final By orderPlacedHeading =
            By.xpath("//h2[normalize-space()='Order Placed!']");

    public OrderConfirmationPage(WebDriver driver) {
        super(driver);
    }

    public boolean isOrderPlacedSuccessfully() {
        return waitForVisible(orderPlacedHeading).isDisplayed();
    }
}