package com.benke.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PaymentPage extends BasePage {

    private final By paymentHeading =
            By.cssSelector("#cart_items .step-one h2.heading");

    private final By nameOnCard =
            By.name("name_on_card");

    private final By cardNumber =
            By.name("card_number");

    private final By cvc =
            By.name("cvc");

    private final By expiryMonth =
            By.name("expiry_month");

    private final By expiryYear =
            By.name("expiry_year");

    private final By payAndConfirmButton =
            By.id("submit");

    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    public boolean isPaymentPageDisplayed() {
        handleBlockingAds();
        return waitForVisible(paymentHeading).isDisplayed();
    }

    public void enterPaymentDetails(
            String cardHolder,
            String number,
            String securityCode,
            String month,
            String year) {

        type(nameOnCard, cardHolder);
        type(cardNumber, number);
        type(cvc, securityCode);
        type(expiryMonth, month);
        type(expiryYear, year);
    }

    public OrderConfirmationPage submitPayment() {
        click(payAndConfirmButton);
        return new OrderConfirmationPage(driver);
    }
}
