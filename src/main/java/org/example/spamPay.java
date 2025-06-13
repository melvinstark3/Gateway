package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class spamPay extends browserSetup{
    public spamPay(){
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//iframe[@title='Secure card payment input frame']")).click();
        WebElement stripeIframe = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//iframe[contains(@name, '__privateStripeFrame')]")
        ));
        driver.switchTo().frame(stripeIframe);
        driver.findElement(By.xpath("//div[@class=\"CardNumberField CardNumberField--ltr\"]")).sendKeys("4242424242424242");
        driver.findElement(By.name("cardnumber")).sendKeys(readProperty("guestNewCardNumber"));
        driver.findElement(By.name("exp-date")).sendKeys(readProperty("expiry"));
        driver.findElement(By.name("cvc")).sendKeys(readProperty("cvv"));
        driver.findElement(By.name("postal")).sendKeys(readProperty("postalCode"));
        driver.switchTo().defaultContent();
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
