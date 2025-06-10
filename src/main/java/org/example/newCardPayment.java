package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

public class newCardPayment extends browserSetup{

    public newCardPayment(String cardNumber, boolean loggedIn){
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@title='Secure card payment input frame']")));
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        new defaultSaveCardCheckbox();
        driver.findElement(By.xpath("//iframe[@title='Secure card payment input frame']")).click();
        WebElement stripeIframe = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//iframe[contains(@name, '__privateStripeFrame')]")
        ));
        driver.switchTo().frame(stripeIframe);
        driver.findElement(By.xpath("//div[@class=\"CardNumberField CardNumberField--ltr\"]")).sendKeys("4242424242424242");
        driver.findElement(By.name("cardnumber")).sendKeys(cardNumber);
        driver.findElement(By.name("exp-date")).sendKeys(readProperty("expiry"));
        driver.findElement(By.name("cvc")).sendKeys(readProperty("cvv"));
        driver.findElement(By.name("postal")).sendKeys(readProperty("postalCode"));
        driver.switchTo().defaultContent();
        driver.findElement(By.id("submit-button")).click();
        System.out.println("Proceeding Payment with New Card");


    }

}
