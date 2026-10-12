package com.benke.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class SignupPage extends BasePage {

    private static final String SIGNUP_URL =
            "https://automationexercise.com/login";

    private final By signupName =
            By.cssSelector("input[data-qa='signup-name']");

    private final By signupEmail =
            By.cssSelector("input[data-qa='signup-email']");

    private final By signupButton =
            By.cssSelector("button[data-qa='signup-button']");

    private final By accountInformationHeading =
            By.xpath("//b[contains(text(),'Enter Account Information')]");


    private final By titleMr = By.id("id_gender1");
    private final By passwordInput = By.id("password");

    private final By daySelect = By.id("days");
    private final By monthSelect = By.id("months");
    private final By yearSelect = By.id("years");

    private final By firstNameInput = By.id("first_name");
    private final By lastNameInput = By.id("last_name");
    private final By addressInput = By.id("address1");
    private final By countrySelect = By.id("country");
    private final By stateInput = By.id("state");
    private final By cityInput = By.id("city");
    private final By zipcodeInput = By.id("zipcode");
    private final By mobileInput = By.id("mobile_number");

    private final By createAccountButton =
            By.cssSelector("button[data-qa='create-account']");

    private final By accountCreatedHeading =
            By.cssSelector("[data-qa='account-created']");

    private final By continueButton =
            By.cssSelector("[data-qa='continue-button']");

    private final By deleteAccountLink =
            By.cssSelector("a[href='/delete_account']");

    private final By accountDeletedHeading =
            By.cssSelector("[data-qa='account-deleted']");

    public SignupPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openUrl(SIGNUP_URL);
    }

    public void startSignup(String name, String email) {
        type(signupName, name);
        type(signupEmail, email);
        click(signupButton);

        dismissAdIfPresent();
    }

    public boolean isAccountInformationDisplayed() {
        return waitForVisible(accountInformationHeading).isDisplayed();
    }


    public void fillAccountInformation(String password) {
        click(titleMr);
        type(passwordInput, password);

        new Select(waitForVisible(daySelect))
                .selectByVisibleText("10");

        new Select(waitForVisible(monthSelect))
                .selectByVisibleText("May");

        new Select(waitForVisible(yearSelect))
                .selectByVisibleText("1995");

        type(firstNameInput, "Automation");
        type(lastNameInput, "Tester");
        type(addressInput, "123 Test Street");

        new Select(waitForVisible(countrySelect))
                .selectByVisibleText("United States");

        type(stateInput, "California");
        type(cityInput, "Los Angeles");
        type(zipcodeInput, "90001");
        type(mobileInput, "5551234567");
    }

    public void createAccount() {
        try {
            clickCreateAccount();
        } catch (ElementClickInterceptedException e) {
            dismissAdIfPresent();
            clickCreateAccount();
        }
    }

    public void clickCreateAccount() {
        WebElement button = waitForVisible(createAccountButton);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                button
        );

        click(createAccountButton);
    }


    public boolean isAccountCreated() {
        return waitForVisible(accountCreatedHeading).isDisplayed();
    }

    public void continueAfterRegistration() {
        try {
            click(continueButton);
        } catch (ElementClickInterceptedException e) {
            dismissAdIfPresent();
            click(continueButton);
        }
    }

    public void deleteAccount() {
        click(deleteAccountLink);
    }

    public boolean isAccountDeleted() {
        return waitForVisible(accountDeletedHeading).isDisplayed();
    }

    public boolean waitForRegistrationResult() {
        HomePage homePage = new HomePage(driver);

        try {
            return wait.until(d ->
                    isAccountCreatedPageDisplayed()
                            || homePage.isUserLoggedIn()
            );
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isAccountCreatedPageDisplayed() {
        return driver.findElements(accountCreatedHeading)
                .stream()
                .anyMatch(element -> element.isDisplayed());
    }

}
