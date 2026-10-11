package com.benke.tests;

import com.benke.base.BaseTest;
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
}
