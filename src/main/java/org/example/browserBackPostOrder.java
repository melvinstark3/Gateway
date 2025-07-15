package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Objects;

public class browserBackPostOrder extends browserSetup{

    public browserBackPostOrder() {
        wait = new WebDriverWait(driver, 60);
        String restartOrderButtonXpath = "//div[@class='bg-white rounded-xl border border-app-gray-300']//span[@class='border-dashed text-sm font-semibold border px-2 py-0.5 rounded-lg cursor-pointer ml-2'][normalize-space()='Click here to start order again']";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(restartOrderButtonXpath)));
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
