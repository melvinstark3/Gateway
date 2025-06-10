package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class createCart extends browserSetup{

    public createCart(String Location, String itemName) throws InterruptedException {
        String locationXpath = "//h5[normalize-space()='" + Location + "']";
        driver.findElement(By.xpath(locationXpath)).click();
        System.out.println("Selected Location : " + Location);
        try {
            wait = new WebDriverWait(driver, 3);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"Yes\"]")));
            //For Some reason even after Completed, We get Cart reset Popup, Handle it with Yes for now.
            driver.findElement(By.xpath("//button[@data-testid=\"Yes\"]")).click();
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Continuing to Menu");
        }
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@data-testid=\"chooserContinue\"])[2]")));
        driver.findElement(By.xpath("(//button[@data-testid=\"chooserContinue\"])[2]")).click();
        //h5 is being used for Superb List View & h4 is being used for Superb
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[normalize-space()='"+itemName+"']")));
        driver.findElement(By.xpath("//h4[normalize-space()='"+itemName+"']")).click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,2000)", "");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
        driver.findElement(By.id("message")).sendKeys(readProperty("itemComment"));
        driver.findElement(By.xpath("//button[@data-testid=\"addToCart\"]")).click();
        js.executeScript("window.scrollBy(0,100)", "");
        driver.findElement(By.xpath("//a[@id=\"cart-header\"]")).click();
        driver.findElement(By.xpath("//button[@data-testid=\"goToCheckout_desktop\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@aria-label=\"Select Payment Methods\"]")));
    }

}
