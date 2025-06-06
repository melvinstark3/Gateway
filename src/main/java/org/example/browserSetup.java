package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class browserSetup {

    public static WebDriver driver;

    public static WebDriverWait wait;

    public static void invokeBrowser() {
        System.setProperty("webdriver.chrome.driver", "/Users/kartik/Documents/bin/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public static void quitBrowser() {
        driver.close();
        driver.quit();
    }
}
