package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

public class checkTransactionID extends browserSetup{

    public checkTransactionID(String OrderID) throws InterruptedException {
        String DashboardUrl = readProperty("BackendURL");
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
        driver.findElement(By.id("edit-name")).sendKeys(readProperty("adminUsername"));
        driver.findElement(By.id("edit-pass")).sendKeys(readProperty("adminPassword"));
        driver.findElement(By.id("edit-submit")).click();
        System.out.println("Attempting Backend Profile Login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-select-profile")));

        // This Code block is specifically for Support Executive accounts, When using a Specific Test Profile credentials, This isn't required.
        driver.findElement(By.id("edit-select-profile")).clear();
        driver.findElement(By.id("edit-select-profile")).sendKeys(readProperty("profileToMasquerade"));
        driver.findElement(By.id("edit-grant-access")).click();
        System.out.println("Granting Access to Test Profile");
        //Waiting to Grant Profile Access
        Thread.sleep(5000);
        driver.navigate().to("https://demo.onlineorderalert.com/backend/support-executive-revoke-permission");
        driver.findElement(By.xpath("//a[normalize-space()='Masquerade']")).click();
        System.out.println("Masquerading Test Profile");
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
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Order Comment was not found");
        }

        //Try catch block for Transaction ID in order details
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
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("TC_05: FAIL - Transaction ID is not displayed in Order Details");
        }
    }

}
