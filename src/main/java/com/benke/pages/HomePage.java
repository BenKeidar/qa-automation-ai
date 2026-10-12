package com.benke.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {
    
    private static final String URL = "https://automationexercise.com/";

    private final By loggedInUser =
            By.xpath("//a[contains(., 'Logged in as')]");

    private final By logoutButton =
            By.cssSelector("a[href='/logout']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openUrl(URL);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isUserLoggedIn() {
        return !driver.findElements(loggedInUser).isEmpty()
                && driver.findElement(loggedInUser).isDisplayed();
    }


    public boolean waitUntilUserLoggedIn() {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            loggedInUser
                    )
            ).isDisplayed();

        } catch (TimeoutException e) {
            return false;
        }
    }

    public void logout() {
        click(logoutButton);
    }
}