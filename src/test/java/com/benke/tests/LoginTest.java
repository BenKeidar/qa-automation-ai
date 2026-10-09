package com.benke.tests;

import com.benke.base.BaseTest;
import com.benke.pages.BasePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void verifySeleniumWebsiteTitle() {
        BasePage basePage = new BasePage(driver);
        basePage.openUrl("https://www.selenium.dev/");

        String actualTitle = driver.getTitle();

        Assert.assertTrue(
                actualTitle.contains("Selenium"),
                "Page title does not contain 'Selenium'"
        );
    }
}