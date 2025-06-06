package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import java.util.List;

public class gatewayNavigation extends browserSetup{

    public gatewayNavigation() throws InterruptedException {
        new paymentNavigation();
        try {
            List<WebElement> elements = driver.findElements(By.id("new-card"));
            if (!elements.isEmpty()) {
                driver.findElement(By.xpath("//label[@class=\"saved__payment__card add_new_card_btn\"]")).click();
            }
        } catch (NoSuchElementException | TimeoutException e) {
            driver.findElement(By.id("submit-button")).click();
        } finally {
            driver.findElement(By.id("submit-button")).click();
        }
        System.out.println("Checking Hypertext Protocol for Gateway Page");
        new checkHttps();
    }

}
