package com.benke.tests;

import com.benke.base.BaseTest;
import com.benke.pages.LoginPage;
import io.qameta.allure.Allure;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest  extends BaseTest {

    @Test
    public void loginWithInvalidCredentialsShouldShowError() {
        LoginPage loginPage = new LoginPage(driver);

        Allure.step("Open login page", () -> {
            loginPage.open();
        });

        Allure.step("Login with invalid credentials", () -> {
            loginPage.login(
                    "invalid-user@example.com",
                    "wrongPassword123"
            );
        });

        Allure.step("Verify invalid login error message", () -> {
            Assert.assertEquals(
                    loginPage.getLoginErrorMessage(),
                    "Your email or password is incorrect!"
            );
        });
    }
}
