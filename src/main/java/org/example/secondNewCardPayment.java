package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class secondNewCardPayment extends browserSetup{

    public secondNewCardPayment(String cardNumber, boolean loggedIn) {
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[@class=\"payment__for__id\"]")));
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        String SecondCardPaymentOrderID = driver.findElement(By.xpath("//h4[@class=\"payment__for__id\"]")).getText();
        if (loggedIn) {
            //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class=\"card__number\"]")));
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            String maskedCardNumber = driver.findElement(By.xpath("//p[@class=\"card__number\"]")).getText();
            String cardEndingNumber = maskedCardNumber.substring(maskedCardNumber.length() - 4);
            System.out.print("Attempting to Delete Card Ending with : " + cardEndingNumber);
            String tempExpiry = driver.findElement(By.xpath("//p[@class=\"expiry__date\"]")).getText();
            System.out.println(" with Expiry :" + tempExpiry);

            //Delete Card Action
            driver.findElement(By.xpath("//i[@class=\"fa fa-trash\"]")).click();
            String deleteConfirmation = driver.switchTo().alert().getText();
            System.out.println("User is being asked: " + deleteConfirmation);
            System.out.println("Accepting the Alert!");
            driver.switchTo().alert().accept();
            try {
                String recheckedCardNumber = driver.findElement(By.xpath("//p[@class=\"card__number\"]")).getText();
                String recheckedExpiry = driver.findElement(By.xpath("//p[@class=\"expiry__date\"]")).getText();
                if (Objects.equals(recheckedCardNumber, maskedCardNumber) && Objects.equals(recheckedExpiry, maskedCardNumber)) {
                    System.out.println("WARNING! Matching Card Details were found after Delete Attempt");
                } else {
                    System.out.println("TC_37: Pass: Saved Card was Deleted");
                }
            } catch (NoSuchElementException | TimeoutException e) {
                System.out.println("ERROR! No Saved Cards were found. Please Verify the Payment Flow");
            }
        }
        System.out.println("Attempting Payment for Order ID " + SecondCardPaymentOrderID);
        new newCardPayment(cardNumber, loggedIn);
        System.out.println("Proceeding Payment with Second New Card");
    }

}
