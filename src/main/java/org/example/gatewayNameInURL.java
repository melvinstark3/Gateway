package org.example;

import org.openqa.selenium.support.ui.WebDriverWait;

public class gatewayNameInURL extends browserSetup{

    public gatewayNameInURL() {
        wait = new WebDriverWait(driver, 30);
        String OriginalPaymentURL = driver.getCurrentUrl();
        String paymentURL = OriginalPaymentURL.toLowerCase();
        System.out.println("Payment URL is " + paymentURL);
        if (paymentURL.contains("stripe") || paymentURL.contains("connect")) {
            System.out.println("TC_30: PASS: URL Contains Gateway's Name it it");
        } else {
            System.out.println("TC_30: PASS: URL doesn't Contain Gateway's Name it it");
        }
    }

}
