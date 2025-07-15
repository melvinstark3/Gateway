package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class createCart extends browserSetup{

    public createCart(String Location, String itemName, boolean loggedIn) throws InterruptedException {
        wait = new WebDriverWait(driver, 30);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,2000)", "");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[normalize-space()='" + Location + "']"))).click();
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
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h4[normalize-space()='"+itemName+"']")));
        driver.findElement(By.xpath("//h4[normalize-space()='"+itemName+"']")).click();
        wait = new WebDriverWait(driver, 3);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"addToCart\"]")));
            js.executeScript("window.scrollBy(0,2000)", "");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message"))).clear();
            driver.findElement(By.id("message")).sendKeys(readProperty("itemComment"));
            driver.findElement(By.xpath("//button[@data-testid=\"addToCart\"]")).click();
        }
        catch (NoSuchElementException | TimeoutException e){
            System.out.println("Adding non Customisable Item to the Cart.");
        }
        js.executeScript("window.scrollBy(0,10)", "");
        try {
            driver.findElement(By.xpath("//button[@data-testid=\"goToCheckout_desktop\"]")).click();
            System.out.println("Cart Popup Appeared Automatically. Proceeding to Checkout");
        }
        catch (NoSuchElementException | TimeoutException e){
            driver.findElement(By.xpath("//a[@id=\"cart-header\"]")).click();
            driver.findElement(By.xpath("//button[@data-testid=\"goToCheckout_desktop\"]")).click();
            System.out.println("Proceeding to Checkout");

        }
        wait = new WebDriverWait(driver, 30);
        if (loggedIn) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@aria-label=\"Select Payment Methods\"]")));
        }
    }

}
