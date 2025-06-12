package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Objects;

public class matchAmount extends browserSetup {
    public matchAmount(String checkoutOrderTotal) {
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[@class=\"total__amount\"]")));
        String gatewayOrderTotal = driver.findElement(By.xpath("//h4[@class=\"total__amount\"]")).getText();
        if(Objects.equals(checkoutOrderTotal, gatewayOrderTotal)){
            System.out.println("TC 56, 30: PASS: Checkout Order Total and Currency Matches with Gateway");
        }
        else {
            System.out.println("TC 56, 30: FAIL: Order Totals/Currency Doesn't Match.");
        }
    }
}