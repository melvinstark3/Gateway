package org.example;

import org.openqa.selenium.By;

public class paymentPageCancellation extends guestOrder{
    public static void main(String[] args) throws InterruptedException {
        invokeBrowser();
        orderFlow();
        paymentPageCancellation();
    }
}
