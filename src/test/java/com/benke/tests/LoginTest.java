package com.benke.tests;

import com.benke.base.BaseTest;
import com.benke.pages.HomePage;
import com.benke.pages.LoginPage;
import io.qameta.allure.Allure;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

public class LoginTest  extends BaseTest {

    @DataProvider(name = "invalidLoginData")
    public Object[][] invalidLoginData() {
        return new Object[][] {
                {"unknown@example.com", "wrongPassword123"},
                {"invalid-user@example.com", "incorrectPass456"},
                {"nobody@example.org", "testPassword789"}
        };
    }

    @Test(dataProvider = "invalidLoginData")
    public void loginWithInvalidCredentialsShouldShowError(
            String email,
            String password
    ) {
        LoginPage loginPage = new LoginPage(driver);

        Allure.step("Open login page", () -> {
            loginPage.open();
        });

        Allure.step("Login with invalid credentials for email: " + email, () -> {
            loginPage.login(email, password);
        });

        Allure.step("Verify invalid login error message", () -> {
            Assert.assertEquals(
                    loginPage.getLoginErrorMessage(),
                    "Your email or password is incorrect!"
            );
        });
    }


    @Test
    public void validLoginAndLogoutShouldSucceed() {

        String email = System.getenv("TEST_USER_EMAIL");
        String password = System.getenv("TEST_USER_PASSWORD");

        if (email == null || email.isBlank()
                || password == null || password.isBlank()) {
            throw new IllegalStateException(
                    "Missing test user environment variables"
            );
        }

        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        Allure.step("Open login page", loginPage::open);

        Allure.step("Login with valid test user", () -> {
            loginPage.login(email, password);
        });

        Allure.step("Verify user is logged in", () -> {
            Assert.assertTrue(
                    homePage.waitUntilUserLoggedIn(),
                    "User was not logged in"
            );
        });


        Allure.step("Logout", () -> {
            homePage.logout();
        });

        Allure.step("Verify user is logged out", () -> {
            Assert.assertTrue(
                    loginPage.waitUntilUserLoggedOut(),
                    "Login page was not displayed after logout"
            );

            Assert.assertFalse(
                    homePage.isUserLoggedIn(),
                    "User is still logged in after logout"
            );
        });

    }
}
