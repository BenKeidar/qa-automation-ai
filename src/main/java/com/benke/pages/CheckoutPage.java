package com.benke.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CheckoutPage extends BasePage {

    private final OrderItemsComponent orderItems;

    private final By totalAmount =
            By.xpath(
                    "//div[@id='cart_info']//tr[" +
                            ".//*[normalize-space()='Total Amount']" +
                            "]/td[last()]"
            );

    private final By deliveryAddress =
            By.id("address_delivery");

    private final By billingAddress =
            By.id("address_invoice");

    private final By placeOrderButton =
            By.cssSelector("a[href='/payment']");

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


    public boolean isDeliveryAddressDisplayed() {
        return waitForVisible(deliveryAddress).isDisplayed();
    }

    public boolean isBillingAddressDisplayed() {
        return waitForVisible(billingAddress).isDisplayed();
    }

    private List<String> getAddressDetails(By addressLocator) {
        WebElement address = waitForVisible(addressLocator);

        return address.findElements(
                        By.cssSelector("li:not(.address_title)")
                )
                .stream()
                .map(element -> element.getText().trim())
                .filter(text -> !text.isEmpty())
                .toList();
    }

    public List<String> getDeliveryAddressDetails() {
        return getAddressDetails(deliveryAddress);
    }

    public List<String> getBillingAddressDetails() {
        return getAddressDetails(billingAddress);
    }

    private String getRecipientName(By addressLocator) {
        return waitForVisible(addressLocator)
                .findElement(
                        By.cssSelector(".address_firstname.address_lastname")
                )
                .getText()
                .trim();
    }

    public String getDeliveryRecipientName() {
        return getRecipientName(deliveryAddress);
    }

    public String getBillingRecipientName() {
        return getRecipientName(billingAddress);
    }

    public PaymentPage placeOrder() {
        click(placeOrderButton);
        return new PaymentPage(driver);
    }
}