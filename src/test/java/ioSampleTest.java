import java.net.MalformedURLException;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.connection.ConnectionState;
import io.appium.java_client.android.connection.ConnectionStateBuilder;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

public class ioSampleTest {
    private AndroidDriver<MobileElement> driver;
    private WebDriverWait wait;


    //Elements By
    By firstButtonBy = By.id("com.example.appiumtest:id/button_first");
    By allowWhenUsingBy = By.id("com.android.permissioncontroller:id/permission_allow_foreground_only_button");
    By secondBy = By.id("com.example.appiumtest:id/button_sec");
    By crossButtonBy = By.id("com.example.appiumtest:id/crossBtn");
    By toolBarTitleBy   = By.id("com.example.appiumtest:id/toolbarTitle");
    
  @BeforeTest
  public void setupTest() {
      driver = BeforeSuites.tLocalDriver.get();
      wait = BeforeSuites.tLocalWait.get();
      System.out.println("setupTest");
      System.out.println("Thread: "  + ": "+ Thread.currentThread().getName());
  }

//    @BeforeSuite(alwaysRun = true)
//    public static  void setup() throws MalformedURLException {
//        System.out.println("setup");
//    }

    //@Test
    public void basicTest() throws InterruptedException {
        //Click and pass Splash
        wait.until(ExpectedConditions.visibilityOfElementLocated(crossButtonBy)).click();

        //Click I am searching a job
        wait.until(ExpectedConditions.visibilityOfElementLocated(secondBy)).click();

        //Notification Allow
        if (wait.until(ExpectedConditions.visibilityOfElementLocated(allowWhenUsingBy)).isDisplayed()) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(allowWhenUsingBy)).click();
        }

        //Click Second Job
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(firstButtonBy)).get(1).click();

        //Do a simple assertion
        String toolBarTitleStr = wait.until(ExpectedConditions.visibilityOfElementLocated(toolBarTitleBy)).getText();
        Assert.assertTrue(toolBarTitleStr.toLowerCase().contains("detay"));
    }

    @Test(priority = 1)
    public void click1stButton1() throws InterruptedException {
        System.out.println("click1stButton1");
        System.out.println("Thread: "  + ": "+ Thread.currentThread().getName());
        driver.setConnection(new ConnectionStateBuilder().withWiFiDisabled().withDataDisabled().build());
       // driver.runAppInBackground(Duration.ofMillis(3000));
        driver.getConnection();
        driver.openNotifications();
        Thread.sleep(2000);
        new TouchAction(driver).tap(PointOption.point(new Point(117, 375))).perform();
        new TouchAction(driver).tap(PointOption.point(new Point(80, 80)));
        new TouchAction(driver).tap(PointOption.point(new Point(80, 80)));
        new TouchAction(driver).tap(PointOption.point(new Point(280, 280)));
        new TouchAction(driver).tap(PointOption.point(new Point(380, 380)));
        new TouchAction(driver).tap(PointOption.point(new Point(80, 80)));
        new TouchAction(driver).tap(PointOption.point(new Point(180, 180)));
        ((AndroidDriver) driver).pressKey(new KeyEvent().withKey(AndroidKey.BACK));

        //driver.closeApp();
        driver.toggleWifi();
        pressWifi();
       /* wait.until(ExpectedConditions.visibilityOfElementLocated(firstButtonBy)).click();
        Thread.sleep(3000);
        contextSwitch(2);
        Thread.sleep(2000);
        System.out.println("Test1: " + driver.getCurrentUrl());
        contextSwitch(1);
        wait.until(ExpectedConditions.visibilityOfElementLocated(crossButtonBy)).click();*/

        Thread.sleep(2000);
        connectWifi();
  }

    public void connectWifi() {
        driver.setConnection(new ConnectionStateBuilder().withWiFiEnabled().withDataEnabled().build());
    }

    @SuppressWarnings("rawtypes")
    public void pressWifi(){

            try {
                ((AndroidDriver)driver).toggleWifi();
                Thread.sleep(2000);
            } catch (Exception e ) {
                System.out.println("Error turning off flight mode.");
            }

    }


    @Test(priority = 2)
    public void click1stButton2() throws InterruptedException {
        System.out.println("click1stButton2");
        System.out.println("Thread: bt2"  + ": "+ Thread.currentThread().getName());
        wait.until(ExpectedConditions.visibilityOfElementLocated(secondBy)).click();
        Thread.sleep(2000);
        contextSwitch(2);
        Thread.sleep(2000);
        System.out.println("Test2: " + driver.getCurrentUrl());
        contextSwitch(1);
        wait.until(ExpectedConditions.visibilityOfElementLocated(crossButtonBy)).click();
        Thread.sleep(2000);
    }

    @Test(priority = 3)
    public void click1stButton3() throws InterruptedException {
        System.out.println("click1stButton3");
        System.out.println("Thread: bt3"  + ": "+ Thread.currentThread().getName());
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstButtonBy)).click();
        Thread.sleep(2000);
        contextSwitch(2);
        Thread.sleep(2000);
        System.out.println("Test3: " + driver.getCurrentUrl());
        contextSwitch(1);
        wait.until(ExpectedConditions.visibilityOfElementLocated(crossButtonBy)).click();
        Thread.sleep(2000);
    }

    int index = 0;

    private void contextSwitch(int type) {

        Set<String> contexts = driver.getContextHandles();
        for(String context: contexts) {
            System.out.println("context: " + context);
        }

        switch (type) {
            case 1 : {
                //native
                driver.context("NATIVE_APP");
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {  }
                break;
            }
            case 2 : {
                try {
                    Thread.sleep(2000);

                } catch (Exception e) {  }

                //webview
                driver.context("WEBVIEW_com.example.appiumtest");
                Set<String> windows = driver.getWindowHandles();
                System.out.println("before switch: " + driver.getCurrentUrl());
                for(String context: windows) {
                    System.out.println("windows: " + context);
                }
                System.out.println("currentWindow: "+driver.getWindowHandle());
                List<String> list = driver.getWindowHandles().stream().collect(Collectors.toList());
                driver.switchTo().window(list.get(list.size()-1));
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {  }
                System.out.println("after switch: " +"index:" + index + " :"+ driver.getCurrentUrl());
                index++;
                break;
            }
            default: {

            }
        }
    }
}
