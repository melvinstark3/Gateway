package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

public class newCardPayment extends browserSetup{

    public newCardPayment(String cardNumber){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[@class=\"payment__for__id\"]")));
        new defaultSaveCardCheckbox();
        new cardDetailsInput(cardNumber);
        driver.findElement(By.id("submit-button")).click();
        System.out.println("Proceeding Payment with New Card");
    }
}
