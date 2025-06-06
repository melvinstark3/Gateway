package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Objects;

public class pageBackPostOrder extends browserSetup{

    public pageBackPostOrder() {
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@aria-label=\"Back To Home\"]")));
        driver.findElement(By.xpath("//a[@aria-label=\"Back To Home\"]")).click();
        try {
            String pageBackURL = driver.getCurrentUrl();
            if (Objects.equals(pageBackURL, "https://gateway.demo-ordering.online/en/order/selector")){
                System.out.println("TC 17: Pass: User is redirected to Selector page");
            }
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("TC 17: Fail : User is redirected to "+driver.getCurrentUrl());
        }
    }

}
