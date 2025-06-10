package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Objects;

public class browserBackPostOrder extends browserSetup{

    public browserBackPostOrder() {
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@aria-label=\"Back To Home\"]")));
        driver.navigate().back();
        try {
            String pageBackURL = driver.getCurrentUrl();
            if (Objects.equals(pageBackURL, readProperty("BrowserBackPostOrderURL"))){
                System.out.println("TC 18: Pass: User is redirected to Selector page");
            }
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("TC 18: Fail : User is redirected to "+driver.getCurrentUrl());
        }
    }

}
