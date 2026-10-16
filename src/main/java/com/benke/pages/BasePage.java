
package com.benke.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

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
        WebElement element = waitForClickable(locator);

        scrollToCenter(element);

        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            handleBlockingAds();

            WebElement retryElement = waitForClickable(locator);
            scrollToCenter(retryElement);

            retryElement.click();
        }
    }

    private void scrollToCenter(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                element
        );
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

    protected void hideAnchorAds() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("""
        document.querySelectorAll(
            'ins.adsbygoogle.adsbygoogle-noablate'
        ).forEach(ad => {
            ad.style.setProperty('display', 'none', 'important');
        });
    """);
    }

    protected void handleBlockingAds() {
        dismissAdIfPresent();
        hideAnchorAds();
    }
}