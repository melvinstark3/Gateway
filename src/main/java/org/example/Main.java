package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class Main {
    public static WebDriver driver;

    public static void invokeBrowser() {
        String browser = "chrome";
        System.setProperty("webdriver.chrome.driver", "/Users/kartik/Documents/bin/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public static void quitBrowser() {
        driver.close();
        driver.quit();
    }

    public static void gatewayEnable() throws InterruptedException{
        driver.navigate().to("https://app1.restolabs.com/en");
        driver.findElement(By.id("edit-name")).sendKeys("kartik@restolabs.com");
        driver.findElement(By.id("edit-pass")).sendKeys("kartik12345");
        driver.findElement(By.id("edit-submit")).click();
        Thread.sleep(5000);
        driver.findElement(By.id("edit-select-profile")).clear();
        driver.findElement(By.id("edit-select-profile")).sendKeys("Flamingo(1126908)");
        driver.findElement(By.id("edit-grant-access")).click();
        driver.navigate().to("https://app1.restolabs.com/backend/support-executive-revoke-permission");
        driver.findElement(By.xpath("//a[normalize-space()='Masquerade']")).click();
        driver.navigate().to("https://app1.restolabs.com/backend/payment-config-V2");
        driver.findElement(By.name("location_id")).click();
        WebElement location_dropdown = driver.findElement(By.name("location_id"));
        Select locations = new Select(location_dropdown);
        locations.selectByVisibleText("Flamingo Uno");
        Thread.sleep(5000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,750)", "");
        driver.findElement(By.name("laravel_payment_v3")).click();
        driver.findElement(By.name("CardConnect[enable]")).click();
    }

    public static void main(String[] args) throws InterruptedException {
        invokeBrowser();
        driver.navigate().to("https://flamingo.onlineordering.io/en");
        driver.findElement(By.xpath("//button[@data-testid=\"modeSelect2\"]")).click();
        driver.findElement(By.xpath("//h5[normalize-space()='Flamingo Uno']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//button[@data-testid=\"chooserContinue\"])[2]")).click();
        driver.findElement(By.xpath("//div[@aria-label=\"Truffle Burrata Pizza\"]")).click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,200)", "");
        driver.findElement(By.id("message")).sendKeys("Test Item Comment");
        driver.findElement(By.xpath("//button[@data-testid=\"addToCart\"]")).click();
        driver.findElement(By.xpath("//a[@id=\"cart-header\"]")).click();
        driver.findElement(By.xpath("//button[@data-testid=\"goToCheckout_desktop\"]")).click();
        Thread.sleep(5000);
        driver.findElement(By.xpath("//input[@data-testid=\"first_name\"]")).sendKeys("Test First Name");
        driver.findElement(By.xpath("//input[@data-testid=\"last_name\"]")).sendKeys("Test Last Name");
        driver.findElement(By.xpath("//input[@data-testid=\"phone\"]")).sendKeys("Test number");
        driver.findElement(By.xpath("//button[@class=\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\"]")).click();
        Thread.sleep(5000);
        driver.findElement(By.xpath("//span[@class='cursor-pointer text-red-600 ng-star-inserted']")).click();
        Thread.sleep(5000);
        driver.findElement(By.name("email")).sendKeys("testing123qazw@gmail.com");
        driver.findElement(By.xpath("//button[@data-testid=\"continueAddAddress\"]")).click();
        Thread.sleep(10000);
        js.executeScript("window.scrollBy(0,2000)", "");
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\"paymentMode1\"]")));
        Thread.sleep(5000);
        driver.findElement(By.xpath("//textarea[@placeholder='Note here...']")).sendKeys("Test Order Comment");
        driver.findElement(By.xpath("//input[@data-testid=\"paymentMode1\"]")).click();
        driver.findElement(By.xpath("(//button[@data-testid=\"placeOrder\"])[2]")).click();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        String paymentURL = "";
        Set<String> windowHandles = driver.getWindowHandles();
        String mainWindowHandle = driver.getWindowHandle();

        for (String handle : windowHandles) {
            if (!handle.equals(mainWindowHandle)) {
                driver.switchTo().window(handle);
                System.out.println("Switched to Payment Popup: " + driver.getTitle());
                paymentURL=driver.getCurrentUrl();
                break;
            }
        }
        if(paymentURL.contains("http://")){
            System.out.println("TC_01: Failed-Order URL is not secure as it contains https");
        } else{
            String httpUrl = paymentURL.replaceAll("https://", "http://");
            driver.navigate().to(httpUrl);
            if(httpUrl.contains("http://")){
                System.out.println("TC_01: Failed-Order URL is loaded in http");
            } else if (httpUrl.contains(("https://"))) {
                System.out.println("TC_01: Pass-URL doesn't work in http");
            }
        }
        //driver.findElement(By.id("back-button")).click();
        driver.findElement(By.name("cardnumber")).sendKeys("4242424242424242");
        driver.findElement(By.name("exp-date")).sendKeys("1125");
        driver.findElement(By.name("cvc")).sendKeys("111");
        driver.findElement(By.name("postal")).sendKeys("10001");
        driver.findElement(By.name("submit-button")).click();
    }
}
