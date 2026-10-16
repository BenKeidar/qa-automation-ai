package com.benke.listeners;

import com.benke.base.BaseTest;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IConfigurationListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.nio.file.Files;
import java.nio.file.Path;

import java.io.ByteArrayInputStream;

public class ScreenshotListener
        implements ITestListener, IConfigurationListener {

    @Override
    public void onTestFailure(ITestResult result) {
        attachScreenshot(result);
    }

    @Override
    public void onConfigurationFailure(ITestResult result) {
        System.out.println(
                "Configuration failed: " +
                        result.getMethod().getMethodName()
        );

        attachScreenshot(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("PASSED: " + result.getName());
    }

    private void attachScreenshot(ITestResult result) {

        Object testInstance = result.getInstance();

        if (!(testInstance instanceof BaseTest)) {
            return;
        }

        WebDriver driver = ((BaseTest) testInstance).getDriver();

        if (!(driver instanceof TakesScreenshot)) {
            return;
        }

        try {
            byte[] screenshot =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.BYTES);
            Path screenshotPath = Path.of(
                    "target",
                    "screenshots",
                    "configuration-failure.png"
            );

            Files.createDirectories(screenshotPath.getParent());

            Files.write(screenshotPath, screenshot);

            System.out.println(
                    "Screenshot saved to: " +
                            screenshotPath.toAbsolutePath()
            );

            Allure.getLifecycle().addAttachment(
                    "Screenshot on Failure",
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    null
            );

        } catch (Exception e) {
            System.err.println(
                    "Failed to capture screenshot: " +
                            e.getMessage()
            );
        }
    }
}