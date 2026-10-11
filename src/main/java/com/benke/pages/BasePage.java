
package com.benke.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;


public class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void openUrl(String url) {
        driver.get(url);
    }

    protected WebElement waitForVisible(By locator) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }

    protected WebElement waitForClickable(By locator) {
        return wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );
    }

    protected void click(By locator) {
        waitForClickable(locator).click();
    }

    protected void type(By locator, String text) {
        WebElement element = waitForVisible(locator);
        element.clear();
        element.sendKeys(text);
    }


    public void dismissAdIfPresent() {

        By adFrames = By.cssSelector(
                "iframe[title='Advertisement'], iframe[aria-label='Advertisement']"
        );

        By dismissButton = By.cssSelector(
                "#dismiss-button[role='button']"
        );

        List<WebElement> frames = driver.findElements(adFrames);

        for (WebElement frame : frames) {
            try {
                driver.switchTo().frame(frame);

                List<WebElement> buttons =
                        driver.findElements(dismissButton);

                if (!buttons.isEmpty()
                        && buttons.get(0).isDisplayed()) {

                    buttons.get(0).click();
                    return;
                }

            } catch (NoSuchElementException
                     | ElementClickInterceptedException
                     | StaleElementReferenceException e) {
                // This frame is not a dismissible ad.
            } finally {
                driver.switchTo().defaultContent();
            }
        }
    }

}