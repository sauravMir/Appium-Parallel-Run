import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class BeforeSuites {
    private  AndroidDriver<MobileElement> driver;
    private  WebDriverWait                wait;
    public static ThreadLocal<AndroidDriver<MobileElement>> tLocalDriver = new ThreadLocal<>();
    public static ThreadLocal<WebDriverWait> tLocalWait = new ThreadLocal<>();

    public static void setDriver(AndroidDriver<MobileElement> driver) {
        tLocalDriver.set(driver);
    }

    public static void setWait(WebDriverWait wait) {
        tLocalWait.set(wait);
    }

    //private BeforeSuites() {}

//    public static BeforeSuites getInstance() {
//        System.out.println("getInstance");
//        if(instance == null)
//            instance = new BeforeSuites();
//        return instance;
//    }

//    <!--run the appium server 2 times-->
//appium --address 127.0.0.1 --port 4723 --base-path /wd/hub
//appium --address 127.0.0.1 --port 5723 --base-path /wd/hub


    @BeforeTest(alwaysRun = true)
    @Parameters({"deviceName", "platformversion", "portNumber"})
    public void setup(String deviceName, String platformversion, String portNumber) throws MalformedURLException {
        System.out.println("setup");
        System.out.println("Thread: " + deviceName + ": "+ Thread.currentThread().getName());
        //instance = this;
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", deviceName);
        caps.setCapability("udid", deviceName); //DeviceId from "adb devices" command
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", platformversion);
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("skipUnlock", "true");
        caps.setCapability("appPackage", "com.example.appiumtest");
        caps.setCapability("appActivity", "com.example.appiumtest.MainActivity");
        caps.setCapability("chromedriverExecutableDir", System.getProperty("user.dir") + "chromeDriver/chrome-driver");

        caps.setCapability("app", new File("/Users/test/Documents/AppiumTest/appium-sample-test-master/src/app-release.apk").getAbsolutePath());
        //caps.setCapability("noReset", "true");
        //caps.setCapability("fullReset",true);
        driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:"+portNumber), caps);
        wait = new WebDriverWait(driver, 10);

        setDriver(driver);
        setWait(wait);

    }



}
