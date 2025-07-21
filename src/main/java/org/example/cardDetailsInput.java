package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class cardDetailsInput extends browserSetup{
    public cardDetailsInput(String cardNumber){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,2000)", "");
        driver.findElement(By.xpath("//iframe[@title='Secure card payment input frame']")).click();
        WebElement stripeIframe = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//iframe[contains(@name, '__privateStripeFrame')]")
        ));
        driver.switchTo().frame(stripeIframe);
        driver.findElement(By.xpath("//div[@class=\"CardNumberField CardNumberField--ltr\"]")).sendKeys(cardNumber);
        driver.findElement(By.name("cardnumber")).sendKeys(cardNumber);
        driver.findElement(By.name("exp-date")).sendKeys(readProperty("expiry"));
        driver.findElement(By.name("cvc")).sendKeys(readProperty("cvv"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("postal"))).sendKeys(readProperty("postalCode"));
        //driver.findElement(By.name("postal")).sendKeys(readProperty("postalCode"));
        driver.switchTo().defaultContent();
    }

}
