package org.example;

import org.openqa.selenium.By;

public class savedCardPayment extends browserSetup{

    public savedCardPayment() throws InterruptedException {
        String SavedCardPaymentOrderID = driver.findElement(By.xpath("//h4[@class=\"payment__for__id\"]")).getText();
        System.out.println("Attempting Payment for Order ID " + SavedCardPaymentOrderID);
        String maskedCardNumber = driver.findElement(By.xpath("//p[@class=\"card__number\"]")).getText();
        String cardEndingNumber = maskedCardNumber.substring(maskedCardNumber.length() - 4);
        System.out.println("Saved Card Details: Card Number Ends with : " + cardEndingNumber);
        String tempExpiry = driver.findElement(By.xpath("//p[@class=\"expiry__date\"]")).getText();
        System.out.println("Extracted date:" + tempExpiry);

        //As a saved card is already selected by Default, we are just directly clicking Pay button
        driver.findElement(By.id("submit-button")).click();
        System.out.println("Attempting Payment with Saved Card");
    }

}
