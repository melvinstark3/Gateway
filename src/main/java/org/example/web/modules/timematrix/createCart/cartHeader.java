package org.example.web.modules.timematrix.createCart;

import org.example.core.browserSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

public class cartHeader extends browserSetup {
    public cartHeader(){
        driver.findElement(By.xpath("//a[@id=\"cart-header\"]")).click();
        driver.findElement(By.xpath("//button[@data-testid=\"goToCheckout_desktop\"]")).click();
        System.out.println("Proceeding to Checkout");
        wait = new WebDriverWait(driver, 30);
    }
}
