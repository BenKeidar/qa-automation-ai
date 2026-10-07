package com.benke.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FirstSeleniumTest {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
    }

    @Test
    public void verifySeleniumWebsiteTitle() {

        driver.get("https://www.selenium.dev/");

        String actualTitle = driver.getTitle();

        Assert.assertTrue(
                actualTitle.contains("Selenium"),
                "Page title does not contain 'Selenium'"
        );
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}