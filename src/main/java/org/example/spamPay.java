package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class spamPay extends browserSetup{
    public spamPay(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[@class=\"payment__for__id\"]")));
        new cardDetailsInput(readProperty("guestNewCardNumber"));
        driver.findElement(By.id("submit-button")).click();
        wait = new WebDriverWait(driver, 20);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.id("submit-button"))).click();
            System.out.println("TC 32: FAIL: WARNING! Multiple Pay Clicks Intercepted. Check Case Manually.");
        } catch (TimeoutException | ElementNotInteractableException e) {
            // If button is NOT clickable due to loader overlay: PASS
            System.out.println("TC 32: PASS: Multiple Pay Clicks are not Allowed");
        }
    }
}
