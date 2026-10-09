package com.benke.pages;

import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private static final String URL = "https://www.selenium.dev/";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openUrl(URL);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}