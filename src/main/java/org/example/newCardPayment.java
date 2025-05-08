package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class newCardPayment extends loginOrder {
    public static void newPayment(String cardNumber, boolean loggedIn) throws InterruptedException {
        wait = new WebDriverWait(driver, 30);
        Thread.sleep(5000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@title='Secure card payment input frame']")));
        driver.findElement(By.xpath("//iframe[@title='Secure card payment input frame']")).click();
        WebElement stripeIframe = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//iframe[contains(@name, '__privateStripeFrame')]")
        ));
        driver.switchTo().frame(stripeIframe);
        driver.findElement(By.xpath("//div[@class=\"CardNumberField CardNumberField--ltr\"]")).sendKeys("4242424242424242");
        driver.findElement(By.name("cardnumber")).sendKeys(cardNumber);
        driver.findElement(By.name("exp-date")).sendKeys("04/30");
        driver.findElement(By.name("cvc")).sendKeys("111");
        driver.findElement(By.name("postal")).sendKeys("10001");
        driver.switchTo().defaultContent();
        driver.findElement(By.id("submit-button")).click();
        System.out.println("Proceeding Payment with New Card");
    }

    public static void main(String[] args) throws InterruptedException {
        invokeBrowser();
        loginOrderFlow();
        //Assuming there is no card saved. Implement CheckSavedorNew logic
        //to check if saved card is there not & acc to that, Click new card button & use new card directly
        newPayment("4242424242424242", true);
    }
}
