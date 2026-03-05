package org.example.web.modules.timematrix.createCart;

import org.example.core.browserSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class addMenuItem extends browserSetup {
    public addMenuItem() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class=\"item_title_html\"]")));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class=\"item_title_html\"]")));

        String themeTag;
        //h5 is being used for Superb List View & h4 is being used for Superb
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[normalize-space()='Normal Item']")));
            themeTag = "h4";
        } catch (TimeoutException e) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[normalize-space()='Normal Item']")));
            themeTag = "h5";
        }

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//" + themeTag + "[normalize-space()='Normal Item']")));
        driver.findElement(By.xpath("//" + themeTag + "[normalize-space()='Normal Item']")).click();
        System.out.println("Adding Item 'Normal Item' to the Cart.");
        Thread.sleep(3000);
        js.executeScript("window.scrollBy(0,10)", "");
        //Close the Cart Header, to handle new item Addition when required, We Will click it again if Needed
        try {
            driver.findElement(By.xpath("//button[@data-testid=\"goToCheckout_desktop\"]")).isDisplayed();
            driver.findElement(By.xpath("//a[@id=\"cart-header\"]")).click();
        } catch (NoSuchElementException | TimeoutException ignored){}
    }
}
