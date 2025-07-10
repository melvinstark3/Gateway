package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.util.Properties;

public class browserSetup {

    public static WebDriver driver;

    public static WebDriverWait wait;

    public static void invokeBrowser() {
        System.setProperty("webdriver.chrome.driver", "/Users/kartik/Documents/bin/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public static String readProperty(String key) {
        Properties prop = new Properties();
        try {
            FileInputStream fis = new FileInputStream("/Users/kartik/Desktop/Gateway/src/main/resources/config.properties");
            prop.load(fis);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return prop.getProperty(key);
    }

    public static void quitBrowser() {
        driver.close();
        driver.quit();
    }
}
