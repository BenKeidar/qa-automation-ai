
package com.benke.tests;

import com.benke.base.BaseTest;
import com.benke.pages.HomePage;
import com.benke.pages.LoginPage;
import com.benke.pages.SignupPage;
import com.benke.testdata.TestData;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.UUID;

public class SignupTest extends BaseTest {

    private SignupPage signupPage;
    private LoginPage loginPage;
    private HomePage homePage;
    private boolean registrationSubmitted;
    private String testEmail;
    private String testPassword;

    @Test
    @Description(
            "Verifies that a new user can register successfully " +
                    "using a unique email address, confirms that the account " +
                    "is created and the user is logged in, and deletes " +
                    "the test account during cleanup."
    )
    public void registerNewUserShouldSucceed() {

        registrationSubmitted = false;
        signupPage = new SignupPage(driver);
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);

        String name = TestData.SIGNUP_NAME;

        testEmail = TestData.SIGNUP_EMAIL_PREFIX
                + UUID.randomUUID()
                + TestData.SIGNUP_EMAIL_DOMAIN;

        testPassword = TestData.SIGNUP_PASSWORD;

        Allure.step("Open signup page", signupPage::open);

        Allure.step("Start signup", () -> {
            signupPage.startSignup(name, testEmail);
        });

        Allure.step("Verify account information form", () -> {
            Assert.assertTrue(
                    signupPage.isAccountInformationDisplayed(),
                    "Account information form was not displayed"
            );
        });

        Allure.step("Fill account information", () -> {
            signupPage.fillAccountInformation(testPassword);
        });

        Allure.step("Submit registration", () -> {
            registrationSubmitted = true;
            signupPage.createAccount();
        });

        Allure.step("Verify account created", () -> {
            Assert.assertTrue(
                    signupPage.isAccountCreated(),
                    "Account was not created successfully"
            );
        });

        Allure.step("Continue to homepage", () -> {
            signupPage.continueAfterRegistration();
        });


        Allure.step("Verify user is logged in", () -> {
            signupPage.dismissAdIfPresent();

            Assert.assertTrue(
                    homePage.waitUntilUserLoggedIn(),
                    "User is not logged in after registration"
            );
        });

    }

    @AfterMethod(alwaysRun = true)
    public void cleanupTestAccount() {
        System.out.println("1 - SignupTest cleanup started");
        if (!registrationSubmitted || signupPage == null) {
            return;
        }

        Allure.step("Cleanup: Wait for registration result", () -> {
            signupPage.waitForRegistrationResult();
        });

        Allure.step("Cleanup: Restore logged-in session", () -> {

            if (signupPage.isAccountCreatedPageDisplayed()) {
                signupPage.continueAfterRegistration();
            }

            if (!homePage.isUserLoggedIn()) {
                loginPage.open();
                loginPage.login(testEmail, testPassword);
            }

            Assert.assertTrue(
                    homePage.waitUntilUserLoggedIn(),
                    "Cleanup failed: Could not log in as test user"
            );
        });

        Allure.step("Cleanup: Delete test account", () -> {
            signupPage.deleteAccount();

            Assert.assertTrue(
                    signupPage.isAccountDeleted(),
                    "Cleanup failed: Test account was not deleted"
            );
        });
    }
}
