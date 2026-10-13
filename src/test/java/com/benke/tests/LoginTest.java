package com.benke.tests;

import com.benke.base.BaseTest;
import com.benke.pages.HomePage;
import com.benke.pages.LoginPage;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

public class LoginTest  extends BaseTest {

    @DataProvider(name = "invalidCredentials")
    public Object[][] invalidCredentials() {
        return new Object[][] {
                {"unknown@example.com", "wrongPassword123"},
                {"invalid-user@example.com", "incorrectPass456"},
                {"nobody@example.org", "testPassword789"}
        };
    }

    @Test(dataProvider = "invalidCredentials")
    @Description(
            "Verifies that login attempts with invalid credentials " +
                    "are rejected and an appropriate error message is displayed."
    )
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
    @Description(
            "Verifies that a registered user can log in successfully " +
                    "and log out from the application."
    )
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
