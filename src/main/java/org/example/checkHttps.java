package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class checkHttps extends browserSetup{

    public checkHttps(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[@class=\"payment__for__id\"]")));
        String paymentURL = driver.getCurrentUrl();
        System.out.println("Redirected Payment URL is " + paymentURL);
        //Check for http in the URL. If not, reload the URL in http and recheck if the URL reloaded in https automatically or not
        if (paymentURL.contains("http://")) {
            System.out.println("TC_01: FAIL - Order URL is not secure as it contains http");
        } else {
            String httpUrl = paymentURL.replaceAll("https://", "http://");
            driver.navigate().to(httpUrl);
            String reloadedUrl = driver.getCurrentUrl();
            System.out.println("Relaoded URL is " + reloadedUrl);
            if (reloadedUrl.contains("http://")) {
                System.out.println("TC_01: FAIL - Order URL is loaded in http");
                try{
                    wait = new WebDriverWait(driver, 30);
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"ml-4 text-lg text-gray-500 uppercase tracking-wider\"]")));
                    String protocolText = driver.findElement(By.xpath("//div[@class=\"ml-4 text-lg text-gray-500 uppercase tracking-wider\"]")).getText().toLowerCase();
                    System.out.println(protocolText);
                    if (protocolText.contains("unauthorized access") || protocolText.contains("not found")) {
                        System.out.println("Gateway Doesn't Work in http");
                    } else {
                        System.out.println("Unknown Reload Intercepted!");
                    }
                }
                catch (NoSuchElementException | TimeoutException e){
                    System.out.println("Unknown Reload Intercepted!");
                }
            } else if (reloadedUrl.contains(("https://"))) {
                System.out.println("TC_01: PASS - URL doesn't work in http");
            }
        }
    }

}
