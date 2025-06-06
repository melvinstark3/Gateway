package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class checkSavedOrNew extends browserSetup{

    public checkSavedOrNew(String cardNumber, boolean loggedIn) throws InterruptedException {
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("back-button")));
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,2000)", "");;
            System.out.println("Checking Saved Cards");
            List<WebElement> elements = driver.findElements(By.id("new-card"));
            if (!elements.isEmpty()) {
                new savedCardPayment();
            } else {
                new newCardPayment(cardNumber, loggedIn);
            }
            //Wait for Order ID element
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='pl-1']")));

        } catch (NoSuchElementException | TimeoutException e) {
            try {
                wait = new WebDriverWait(driver, 2);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[normalize-space()='Add New Card']")));
                driver.findElement(By.xpath("//label[normalize-space()='Add New Card']")).click();
            } catch (NoSuchElementException | TimeoutException t) {
                System.out.println("Trying First Fresh Card Payment");
            }
            new newCardPayment(cardNumber, loggedIn);
        }
    }

}
