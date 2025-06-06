package org.example;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

public class paymentIntent extends browserSetup{

    public paymentIntent() {
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        Set<String> windowHandles = driver.getWindowHandles();
        String mainWindowHandle = driver.getWindowHandle();

        for (String handle : windowHandles) {
            if (!handle.equals(mainWindowHandle)) {
                driver.switchTo().window(handle);
                System.out.println("Switched to Payment Window: " + driver.getCurrentUrl());
                break;
            }
        }
    }

}
