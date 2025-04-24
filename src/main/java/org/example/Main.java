package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Main {
    public static WebDriver driver;

    public static WebDriverWait wait;

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

    public static void gatewayEnable() throws InterruptedException {
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
        driver.findElement(By.name("StripeV2[enable]")).click();
    }

    public static void switchToMainContent() throws InterruptedException {
        try {
            String mainWindowHandle = driver.getWindowHandle();
            driver.switchTo().window(mainWindowHandle);
            wait.until(ExpectedConditions.numberOfWindowsToBe(1));
        } catch (NoSuchWindowException e) {
            System.out.println("Payment popup window closed automatically. Continuing with the main window.");
        }
        Thread.sleep(10000);
    }

    public static void paymentIntent() throws InterruptedException {
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        Set<String> windowHandles = driver.getWindowHandles();
        String mainWindowHandle = driver.getWindowHandle();

        for (String handle : windowHandles) {
            if (!handle.equals(mainWindowHandle)) {
                driver.switchTo().window(handle);
                System.out.println("Switched to Payment Window: " + driver.getCurrentUrl());
                break;
            }
        }
    }

    public static void checkHttps() throws InterruptedException {
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit-button")));
        String paymentURL = driver.getCurrentUrl();
        System.out.println("Redirected Payment URL is "+paymentURL);
        //Check for http in the URL. If not, reload the URL in http and recheck if the URL reloaded in https automatically or not
        if (paymentURL.contains("http://")) {
            System.out.println("TC_01: FAIL - Order URL is not secure as it contains http");
        } else {
            String httpUrl = paymentURL.replaceAll("https://", "http://");
            driver.navigate().to(httpUrl);
            Thread.sleep(5000);
            String reloadedUrl = driver.getCurrentUrl();
            System.out.println("Relaoded URL is "+reloadedUrl);
            if (reloadedUrl.contains("http://")) {
                System.out.println("TC_01: FAIL - Order URL is loaded in http");
            } else if (reloadedUrl.contains(("https://"))) {
                System.out.println("TC_01: PASS - URL doesn't work in http");
            }
        }
    }

    public static void gatewayNameInURL() throws InterruptedException {
        wait = new WebDriverWait(driver, 30);
        String OriginalPaymentURL = driver.getCurrentUrl();
        String paymentURL = OriginalPaymentURL.toLowerCase();
        System.out.println("Payment URL is "+paymentURL);
        if (paymentURL.contains("world") || paymentURL.contains("express") ) {
            System.out.println("TC_30: PASS: URL Contains Gateway's Name it it");
        } else {
            System.out.println("TC_30: PASS: URL doesn't Contain Gateway's Name it it");
        }

    }

    public static void checkSavedOrNew(boolean loggedIn) throws InterruptedException {
        try {
            List<WebElement> elements = driver.findElements(By.id("new-card"));
            if (!elements.isEmpty()) {
                savedCardPayment();
            }
            Thread.sleep(10000);

        } catch (NoSuchElementException e) {
            newCardPayment("4111111111111111",loggedIn);
        }
    }

    public static void defaultSaveCardCheckbox() {
        try {
            WebElement checkbox = driver.findElement(By.id("save-card"));
            String saveCardStatement = driver.findElement(By.xpath("//label[@for=\"save-card\"]")).getText();

            if (checkbox.isEnabled()) {
                System.out.println("TC_39: PASS - Save Card is Enabled by Default");
                if (saveCardStatement.equals("Save this card for future payments")) {

                    System.out.println("Save Card Statement is '" + saveCardStatement + "'");
                }
            } else {
                System.out.println("TC_39: FAIL - Save Card is not Enabled By Default");
            }

        } catch (NoSuchElementException e) {
            System.out.println("Save Card Checkbox is not Displayed");
        }
    }

    public static void paymentPageCancellation() throws InterruptedException {
        driver.navigate().to("https://gateway.demo-ordering.online/");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//button[@data-testid=\"modeSelect2\"]")).click();

        String locationXpath = "//h5[normalize-space()='" + "First Location" + "']";
        driver.findElement(By.xpath(locationXpath)).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//button[@data-testid=\"chooserContinue\"])[2]")).click();
        Thread.sleep(3000);
        driver.findElement(By.id("subCategory1204469")).click();
        //This xpath is working for Superb Theme only for now. Will create Xpath for Superb List view if needed
        driver.findElement(By.xpath("//div[@aria-label=\"Mozzarella Sticks\"]")).click();
        driver.findElement(By.xpath("//a[@id=\"cart-header\"]")).click();
        driver.findElement(By.xpath("//button[@data-testid=\"goToCheckout_desktop\"]")).click();
        Thread.sleep(5000);

        driver.findElement(By.xpath("//input[@data-testid=\"paymentMode1\"]")).click();
        driver.findElement(By.xpath("(//button[@data-testid=\"placeOrder\"])[2]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("back-button")));
        driver.findElement(By.id("back-button")).click();

        Thread.sleep(10000);
        System.out.println("Cancellation of Payment takes User to the URL: "+driver.getCurrentUrl());
    }

    public static void gatewayPageCancellation() throws InterruptedException {
        driver.navigate().to("https://gateway.demo-ordering.online/");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//button[@data-testid=\"modeSelect2\"]")).click();

        String locationXpath = "//h5[normalize-space()='" + "First Location" + "']";
        driver.findElement(By.xpath(locationXpath)).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//button[@data-testid=\"chooserContinue\"])[2]")).click();
        Thread.sleep(3000);
        driver.findElement(By.id("subCategory1204469")).click();
        //This xpath is working for Superb Theme only for now. Will create Xpath for Superb List view if needed
        driver.findElement(By.xpath("//div[@aria-label=\"Mozzarella Sticks\"]")).click();
        driver.findElement(By.xpath("//a[@id=\"cart-header\"]")).click();
        driver.findElement(By.xpath("//button[@data-testid=\"goToCheckout_desktop\"]")).click();
        Thread.sleep(5000);

        driver.findElement(By.xpath("//input[@data-testid=\"paymentMode1\"]")).click();
        driver.findElement(By.xpath("(//button[@data-testid=\"placeOrder\"])[2]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("back-button")));
        //Cancel Payment using back button
        driver.findElement(By.id("submit-button")).click();
        System.out.println("Attempting Cancellation on WorldPay Gateway Page");

        driver.findElement(By.id("ctl00_mainPage_btn_Cancel")).click();

        Thread.sleep(10000);
        System.out.println("Cancellation of Payment takes User to the URL: "+driver.getCurrentUrl());
    }

    public static void secondNewCardPayment(String cardNumber, boolean loggedIn) throws InterruptedException {
        Thread.sleep(5000);
        if(loggedIn) {
            String SecondCardPaymentOrderID = driver.findElement(By.xpath("//h4[@class=\"payment__for__id\"]")).getText();

            String maskedCardNumber = driver.findElement(By.xpath("//p[@class=\"card__number\"]")).getText();
            String cardEndingNumber = maskedCardNumber.substring(maskedCardNumber.length() - 4);
            System.out.print("Attempting to Delete Card Ending with : " + cardEndingNumber);
            String tempExpiry = driver.findElement(By.xpath("//p[@class=\"expiry__date\"]")).getText();
            System.out.println(" with Expiry :" + tempExpiry);

            //Delete Card Action
            driver.findElement(By.xpath("//i[@class=\"fa fa-trash\"]")).click();
            try {

                String recheckedCardNumber = driver.findElement(By.xpath("//p[@class=\"card__number\"]")).getText();
                String recheckedExpiry = driver.findElement(By.xpath("//p[@class=\"expiry__date\"]")).getText();
                if (Objects.equals(recheckedCardNumber, maskedCardNumber) && Objects.equals(recheckedExpiry, maskedCardNumber)){
                    System.out.println("WARNING! Matching Card Details were found after Delete Attempt");
                } else {
                    System.out.println("TC_37: Pass: Saved Card was Deleted");
                }
            } catch (NoSuchElementException e) {
                System.out.println("ERROR! No Saved Cards were found. Please Verify the Payment Flow");
            }

            System.out.println("Attempting Payment for Order ID " + SecondCardPaymentOrderID);
            driver.findElement(By.id("new-card")).click();
            driver.findElement(By.id("submit-button")).click();
        }
        Thread.sleep(5000);
        System.out.println("WordPay Express Page URL is "+driver.getCurrentUrl());
        String OrderIDonGatewayPage = driver.findElement(By.id("ctl00_mainPage_lbl_WelcomeText")).getText();
        System.out.println("Entering Card Details for "+OrderIDonGatewayPage);

        driver.findElement(By.id("ctl00_mainPage_txt_CardNumber")).sendKeys(cardNumber);

        WebElement expMonth = driver.findElement(By.id("ctl00_mainPage_ddl_ExpirationMonth"));
        Select expMonthDropdown = new Select(expMonth);
        expMonthDropdown.selectByVisibleText("05");

        WebElement expYear = driver.findElement(By.id("ctl00_mainPage_ddl_ExpirationYear"));
        Select expYearDropdown = new Select(expYear);
        expYearDropdown.selectByVisibleText("2027");
        driver.findElement(By.id("txt_CVV")).sendKeys("123");
        driver.findElement(By.id("btn_Submit")).click();
        System.out.println("Attempting Payment with Second New Card");
    }

    public static void newCardPayment(String cardNumber, boolean loggedIn) throws InterruptedException {
        Thread.sleep(5000);
        defaultSaveCardCheckbox();
        if(loggedIn) {

            gatewayNameInURL();
            System.out.println("Checking HTTP/HTTPS for Payment Page URL");
            checkHttps();
            String SavedCardPaymentOrderID = driver.findElement(By.xpath("//h4[@class=\"payment__for__id\"]")).getText();
            System.out.println("Attempting Payment for Order ID " + SavedCardPaymentOrderID);
            driver.findElement(By.id("submit-button")).click();
        }
        Thread.sleep(5000);
        gatewayNameInURL();
        System.out.println("WordPay Express Page URL is "+driver.getCurrentUrl());
        String OrderIDonGatewayPage = driver.findElement(By.id("ctl00_mainPage_lbl_WelcomeText")).getText();
        System.out.println("Entering Card Details for "+OrderIDonGatewayPage);

        driver.findElement(By.id("ctl00_mainPage_txt_CardNumber")).sendKeys(cardNumber);

        WebElement expMonth = driver.findElement(By.id("ctl00_mainPage_ddl_ExpirationMonth"));
        Select expMonthDropdown = new Select(expMonth);
        expMonthDropdown.selectByVisibleText("05");

        WebElement expYear = driver.findElement(By.id("ctl00_mainPage_ddl_ExpirationYear"));
        Select expYearDropdown = new Select(expYear);
        expYearDropdown.selectByVisibleText("2027");
        driver.findElement(By.id("txt_CVV")).sendKeys("123");
        driver.findElement(By.id("btn_Submit")).click();
        System.out.println("Attempting Payment with New Card");
    }

    public static void savedCardPayment() throws InterruptedException {
        String SavedCardPaymentOrderID=driver.findElement(By.xpath("//h4[@class=\"payment__for__id\"]")).getText();
        System.out.println("Attempting Payment for Order ID "+SavedCardPaymentOrderID);
        String maskedCardNumber = driver.findElement(By.xpath("//p[@class=\"card__number\"]")).getText();
        String cardEndingNumber = maskedCardNumber.substring(maskedCardNumber.length() - 4);
        System.out.println("Saved Card Details: Card Number Ends with : " + cardEndingNumber);
        String tempExpiry = driver.findElement(By.xpath("//p[@class=\"expiry__date\"]")).getText();
        System.out.println("Extracted date:" + tempExpiry);

        //As a saved card is already selected by Default, we are just directly clicking Pay button
        driver.findElement(By.id("submit-button")).click();
        System.out.println("Payment Proceeded with Saved Card");
    }

    public static void createCart(String Location) throws InterruptedException {
        String locationXpath = "//h5[normalize-space()='" + Location + "']";
        driver.findElement(By.xpath(locationXpath)).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//button[@data-testid=\"chooserContinue\"])[2]")).click();
        Thread.sleep(3000);
        //This xpath is working for Superb Theme only for now. Will create Xpath for Superb List view if needed
        driver.findElement(By.xpath("//div[@aria-label=\"Mama-Mia\"]")).click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,2000)", "");
        driver.findElement(By.id("message")).sendKeys("Test Item Comment");
        driver.findElement(By.xpath("//button[@data-testid=\"addToCart\"]")).click();
        driver.findElement(By.xpath("//a[@id=\"cart-header\"]")).click();
        driver.findElement(By.xpath("//button[@data-testid=\"goToCheckout_desktop\"]")).click();
        Thread.sleep(5000);
    }

    public static void checkTransactionID(String OrderID) throws InterruptedException {
        String DashboardUrl = "https://demo.onlineorderalert.com/en";
        ((JavascriptExecutor) driver).executeScript("window.open('" + DashboardUrl + "', '_blank');");
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        Set<String> windowHandles = driver.getWindowHandles();
        String mainWindowHandle = driver.getWindowHandle();
        String newTabHandle = "";
        for (String handle : windowHandles) {
            if (!handle.equals(mainWindowHandle)) {
                newTabHandle = handle;
                break;
            }
        }
        driver.switchTo().window(newTabHandle);
        driver.findElement(By.id("edit-name")).sendKeys("kartik@restolabs.com");
        driver.findElement(By.id("edit-pass")).sendKeys("kartik123");
        driver.findElement(By.id("edit-submit")).click();
        Thread.sleep(5000);

        // This Code block is specifically for Support Executive accounts, When using a Specific Test Profile credentials, This isn't required.
        driver.findElement(By.id("edit-select-profile")).clear();
        driver.findElement(By.id("edit-select-profile")).sendKeys("Gateway (1204445)");
        driver.findElement(By.id("edit-grant-access")).click();
        driver.navigate().to("https://app1.restolabs.com/backend/support-executive-revoke-permission");
        driver.findElement(By.xpath("//a[normalize-space()='Masquerade']")).click();

        //Change the Tab if needed, as per Order Status. By Default We select pending orders for new orders
        driver.findElement(By.xpath("//button[@data-tab=\"pending_orders\"]")).click();
        //driver.findElement(By.xpath("//button[@data-tab=\"confirmed_orders\"]")).click();
        driver.findElement(By.linkText(OrderID)).click();

        //try catch block for Item Comment
        try {
            List<WebElement> itemPoints = driver.findElements(By.xpath("//span[@tabindex='0']"));

            if (!itemPoints.isEmpty()) {
                WebElement lastElement = itemPoints.get(itemPoints.size() - 1); // Get the last element
                String lastElementText = lastElement.getText();
                System.out.println("Item Comment: " + lastElementText);
            } else {
                System.out.println("No Item Points");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Order Comment was not found");
        }

        //Try catch block for Transcation ID in order details
        try {
            List<WebElement> OrderDetails = driver.findElements(By.xpath("//div[@class=\"delivery-label\"]"));
            int DetailNumber = 1;
            boolean transactionIDfound = false;
            String TransID = "";
            for (WebElement EachDetail : OrderDetails) {
                String CommentText = EachDetail.getText();
                if (CommentText.contains("Transaction ID")) {
                    String TransIDxpath = "(//div[@class=\"delivery-value\"])[" + DetailNumber + "]";
                    TransID = driver.findElement(By.xpath(TransIDxpath)).getText();
                    transactionIDfound = true;
                    break;
                } else {
                    //Don't ask me why I haven't written this as DetailNumber++, I don't know what sorcery Java does
                    //but if I mess this up, Either the increment or this variable's initialization becomes unused.
                    DetailNumber = DetailNumber + 1;
                }
            }
            System.out.println(transactionIDfound ? "TC_05: PASS - Transaction ID is displayed in Order Details\nTransaction ID for Order ID " + OrderID + " is " + TransID : "TC_05: FAIL - Transaction ID is not displayed in Order Details");


            //Keep this Boolean initialization above OrderComments for loop only else,
            //Thanks to Java's Sorcery, if this is mentioned anywhere else,
            // all the other "for loops" in this function will be skipped
            boolean transIDCommented = false;
            List<WebElement> orderComments = driver.findElements(By.xpath("//td[@class=\"message\"]"));
            for (WebElement eachComment : orderComments) {
                String CommentText = eachComment.getText();
                if (CommentText.equals(TransID)) {
                    transIDCommented = true;
                } else {
                    transIDCommented = false;
                }
            }
            System.out.println(transIDCommented ? "TC_04: FAIL - Transaction ID is displayed in Comments" : "TC_04: PASS - Transaction ID is not displayed in Order Comments");
        } catch (NoSuchElementException e) {
            System.out.println("TC_05: FAIL - Transaction ID is not displayed in Order Details");
        }
    }

    public static void restartOrderWithData(boolean loggedIn) throws InterruptedException {
        String restartOrderButtonXpath = "//div[@class='bg-white rounded-xl border border-app-gray-300']//span[@class='border-dashed text-sm font-semibold border px-2 py-0.5 rounded-lg cursor-pointer ml-2'][normalize-space()='Click here to start order again']";
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(restartOrderButtonXpath)));
        driver.findElement(By.xpath(restartOrderButtonXpath)).click();
        createCart("Second Location");
        driver.findElement(By.xpath("//input[@data-testid=\"paymentMode1\"]")).click();
        driver.findElement(By.xpath("(//button[@data-testid=\"placeOrder\"])[2]")).click();
        System.out.print("For 2nd Restart Order: ");
        secondNewCardPayment("4911830000000", loggedIn);
    }

    public static void loginOrder() throws InterruptedException {
        boolean loggedIn = true;
        driver.navigate().to("https://gateway.demo-ordering.online/");
        driver.findElement(By.id("guest_user")).click();
        driver.findElement(By.id("email")).sendKeys("testing123qazw@gmail.com");
        driver.findElement(By.id("password")).sendKeys("12345678");
        driver.findElement(By.xpath("//button[@data-testid=\"login\"]")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//button[@data-testid=\"modeSelect2\"]")).click();
        createCart("First Location");
        Thread.sleep(10000);
        driver.findElement(By.xpath("//textarea[@placeholder='Note here...']")).sendKeys("Test Order Comment");
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\"paymentMode1\"]")));
        //Select Payment method (paymentMode0 = 1st - COD , paymentMode1 = 2nd - Online)
        driver.findElement(By.xpath("//input[@data-testid=\"paymentMode1\"]")).click();
        driver.findElement(By.xpath("(//button[@data-testid=\"placeOrder\"])[2]")).click();
        System.out.print("For Logged In Order: ");
        paymentPageCancellation();
        gatewayPageCancellation();
        checkSavedOrNew(loggedIn);
        restartOrderWithData(loggedIn);
        Thread.sleep(5000);
        String orderIWithHash = driver.findElement(By.xpath("//span[@class='pl-1']")).getText();
        String OrderID = orderIWithHash.replace("#", "");
        System.out.println("TC_06: PASS - Order placed by Logged In User.");
        System.out.println("TC_20: PASS - Payment Gateway is working for a Single Location");
        // Check Transaction only in either Guest/Login for now. I will make it dynamic later to check
        // if user is already logged in and skip the login process in such case
        checkTransactionID(OrderID);
    }

    public static void guestOrder() throws InterruptedException {
        boolean loggedIn = false;
        driver.navigate().to("https://gateway.demo-ordering.online/");
        driver.findElement(By.xpath("//button[@data-testid=\"modeSelect2\"]")).click();
        createCart("First Location");
        driver.findElement(By.xpath("//input[@data-testid=\"first_name\"]")).sendKeys("Test First Name");
        driver.findElement(By.xpath("//input[@data-testid=\"last_name\"]")).sendKeys("Test Last Name");
        driver.findElement(By.xpath("//input[@data-testid=\"phone\"]")).sendKeys("Test number");
        driver.findElement(By.xpath("//button[@class=\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\"]")).click();
        Thread.sleep(5000);
        driver.findElement(By.xpath("//span[@class='cursor-pointer text-red-600 ng-star-inserted']")).click();
        Thread.sleep(3000);
        driver.findElement(By.name("email")).sendKeys("testing123qazw@gmail.com");
        driver.findElement(By.xpath("//button[@data-testid=\"continueAddAddress\"]")).click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,2000)", "");
        Thread.sleep(10000);
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\"paymentMode1\"]")));
        driver.findElement(By.xpath("//textarea[@placeholder='Note here...']")).sendKeys("Test Order Comment");
        Thread.sleep(10000);
        System.out.print("TC_07: For Guest Order: ");
        //Select Payment method (paymentMode0 = COD, paymentMode1=Online)
        driver.findElement(By.xpath("//input[@data-testid=\"paymentMode0\"]")).click();
        System.out.println("TC_32: Cash on Delivery Payment wih Gateway");
        driver.findElement(By.xpath("(//button[@data-testid=\"placeOrder\"])[2]")).click();
        restartOrderWithData(loggedIn);
        String orderIWithHash = driver.findElement(By.xpath("//span[@class='pl-1']")).getText();
        String OrderID = orderIWithHash.replace("#", "");
        System.out.println("TC_12: PASS - Payment Successful by a New Card");
        System.out.println("TC_20: PASS - Payment Gateway is working for a Single Location");
    }

    public static void main(String[] args) throws InterruptedException {
        invokeBrowser();
        guestOrder();
        loginOrder();
    }
}
