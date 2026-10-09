package com.benke.tests;

import com.benke.base.BaseTest;
import com.benke.pages.BasePage;
import com.benke.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void verifySeleniumWebsiteTitle() {
        HomePage homePage = new HomePage(driver);

        homePage.open();

        String actualTitle = homePage.getPageTitle();

        Assert.assertTrue(
                actualTitle.contains("Selenium"),
                "Page title does not contain 'Selenium'"
        );
    }
}