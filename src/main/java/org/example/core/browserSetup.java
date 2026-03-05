package org.example.core;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class browserSetup {

    public static WebDriver driver;

    public static WebDriverWait wait;

    private static Properties properties = new Properties();

    public static moduleSelector getModule = new moduleSelector();

    public static String readProperty(String key) {
        try {
            if (properties.isEmpty()) {
                String module = System.getProperty("module");
                InputStream commonConfig = browserSetup.class.getClassLoader()
                        .getResourceAsStream("common.properties");
                properties.load(commonConfig);
                InputStream moduleConfig = browserSetup.class.getClassLoader()
                        .getResourceAsStream(module + ".properties");
                properties.load(moduleConfig);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return properties.getProperty(key);
    }

    public static String takeScreenshot() {
        if (driver == null) {
            System.out.println("Driver is null, cannot take screenshot.");
            return null;
        }

        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destination = System.getProperty("user.dir") + "/screenshots/" + dateName + ".png";
        File finalDestination = new File(destination);

        try {
            FileUtils.copyFile(source, finalDestination);
            System.out.println("Screenshot taken: " + destination);
            return destination;
        } catch (IOException e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }

    public static void invokeBrowser() {
        System.setProperty("webdriver.chrome.driver", "/Users/kartik/Documents/bin/chromedriver");
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("autofill.credit_card_enabled", false);
        prefs.put("autofill.profile_enabled", false);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        String platform = System.getProperty("platform");

        if (platform.equalsIgnoreCase("web")) {

            driver.manage().window().maximize();
            System.out.println("Platform = WEB → Maximized window");

        } else if (platform.equalsIgnoreCase("mobile")) {

            driver.manage().window().setSize(new Dimension(1206, 2622));
            System.out.println("Platform = MOBILE → iPhone 17 Pro (1206x2622)");

        }
    }

    public static void quitBrowser() {
        driver.close();
        driver.quit();
        System.out.println("Nuking ChromeDriver Instance");
    }
}
