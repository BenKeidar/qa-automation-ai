
package com.benke.listeners;

import com.benke.base.BaseTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;

public class ScreenshotListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {

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


            Allure.getLifecycle().addAttachment(
                    "Screenshot on Failure",
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    null
            );

        } catch (Exception e) {
            System.err.println(
                    "Failed to capture screenshot: " + e.getMessage()
            );
        }
    }
}
