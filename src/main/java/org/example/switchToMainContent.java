package org.example;

import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class switchToMainContent extends browserSetup{

    public switchToMainContent() {
        try {
            String mainWindowHandle = driver.getWindowHandle();
            driver.switchTo().window(mainWindowHandle);
            wait.until(ExpectedConditions.numberOfWindowsToBe(1));
        } catch (NoSuchWindowException e) {
            System.out.println("Payment popup window closed automatically. Continuing with the main window.");
        }
    }

}
