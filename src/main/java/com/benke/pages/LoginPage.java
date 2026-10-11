
package com.benke.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.TimeoutException;

public class LoginPage extends BasePage {

    private static final String LOGIN_URL =
            "https://automationexercise.com/login";

    private final By emailInput =
            By.cssSelector("input[data-qa='login-email']");

    private final By passwordInput =
            By.cssSelector("input[data-qa='login-password']");

    private final By loginButton =
            By.cssSelector("button[data-qa='login-button']");

    private final By loginError =
            By.xpath("//p[contains(text(),'Your email or password is incorrect!')]");

    private final By loggedInUser =
            By.xpath("//a[contains(., 'Logged in as')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openUrl(LOGIN_URL);
    }

    public void login(String email, String password) {
        type(emailInput, email);
        type(passwordInput, password);
        click(loginButton);

        dismissAdIfPresent();
    }

    public String getLoginErrorMessage() {
        return waitForVisible(loginError).getText();
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
}
