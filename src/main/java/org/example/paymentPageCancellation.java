package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class paymentPageCancellation extends browserSetup{

    public paymentPageCancellation() throws InterruptedException {
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("back-button")));
        driver.findElement(By.id("back-button")).click();
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cart-header"))).isDisplayed();
            System.out.println("User is redirected to Menu");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("User is not redirected to Menu");
        }
        System.out.println("Cancellation of Payment takes User to the URL: " + driver.getCurrentUrl());
    }

}
