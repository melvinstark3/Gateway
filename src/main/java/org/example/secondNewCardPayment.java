package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class secondNewCardPayment extends browserSetup{

    public secondNewCardPayment(String cardNumber, boolean loggedIn) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[@class=\"payment__for__id\"]")));
        String SecondCardPaymentOrderID = driver.findElement(By.xpath("//h4[@class=\"payment__for__id\"]")).getText();
        if (loggedIn) {
            new deleteCard();
        }
        System.out.println("Attempting Payment for Order ID " + SecondCardPaymentOrderID);
        new newCardPayment(cardNumber);
        System.out.println("Proceeding Payment with Second New Card");
    }

}
