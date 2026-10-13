package com.benke.tests;

import com.benke.base.BaseTest;
import com.benke.pages.HomePage;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VerifyHomePageTitleTest extends BaseTest {

    @Test
    @Description("Verifies that the home page displays the expected browser title.")
    void verifyHomePageTitle() {
        HomePage homePage = new HomePage(driver);

        homePage.open();

        String actualTitle = homePage.getPageTitle();

        Assert.assertTrue(
                actualTitle.contains("Automation Exercise"),
                "Home page title does not contain 'Automation Exercise'. Actual title: "
                        + actualTitle
        );
    }
}