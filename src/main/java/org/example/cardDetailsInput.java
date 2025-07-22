package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class cardDetailsInput extends browserSetup{
    public cardDetailsInput(String cardNumber) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,2000)", "");
        WebElement ccIframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("tokenFrame")));
        driver.switchTo().frame(ccIframe);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ccnumfield")));
        driver.findElement(By.id("ccnumfield")).sendKeys(cardNumber);
        WebElement monthDropDown = driver.findElement(By.id("ccexpirymonth"));
        Select expiryMonth = new Select(monthDropDown);
        expiryMonth.selectByValue(readProperty("expiryMonth"));
        WebElement yearDropDown = driver.findElement(By.id("ccexpiryyear"));
        Select expiryYear = new Select(yearDropDown);
        expiryYear.selectByValue(readProperty("expiryYear"));
        driver.findElement(By.id("cccvvfield")).sendKeys(readProperty("cvv"));
        driver.switchTo().defaultContent();
        //Card Connect first Masks Card and Accept it as a Valid Input else it would fail Payment even with valid input
        Thread.sleep(3000);
    }

}
