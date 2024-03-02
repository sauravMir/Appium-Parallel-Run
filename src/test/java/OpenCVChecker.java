import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.imagecomparison.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.testng.Assert.assertNotNull;

public class OpenCVChecker {
    String baseUrl = "/Users/test/Documents/AppiumTest/appium-sample-test-master/Appium-Parallel-Run/Images/";

    public void check(AndroidDriver<MobileElement> driver) {

        test(driver);
    }

    public void test(AndroidDriver<MobileElement> driver) {
        String baseImagePath= baseUrl + "baseimage.png";
        String screenshotPath=baseUrl;

        File fileScreenShot = new File(screenshotPath);

        createFile(fileScreenShot);


        byte[] screenshotTemp =driver.getScreenshotAs(OutputType.BYTES);
        byte[] screenshot =Base64.encodeBase64(screenshotTemp);

        takeSnapShot(screenshot, screenshotPath);


        byte[] partial = readImage(baseImagePath);
        byte[] screenShots = readImage(screenshotPath+"abcd.png");

        try {
            OccurrenceMatchingResult result = driver
                    .findImageOccurrence(screenShots, partial, new OccurrenceMatchingOptions()
                            .withEnabledVisualization());
            assertNotNull(result.getRect());

            byte[] opImageData = result.getVisualization();

            byte[] decodedBytes = Base64.decodeBase64(opImageData);

            File opFile = new File(baseUrl + "visual_result.png");

            FileUtils.writeByteArrayToFile(opFile, decodedBytes);

        } catch (IOException e) {

            e.printStackTrace();
        };
    }

    public void createFile(File f) {
        try {
            f.createNewFile();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void takeSnapShot(byte[] screenshot, String filePath1) {
        // Specify the file path where you want to save the byte arra

        // Combine the directory path and file name to get the complete file path
        String filePath = Paths.get(filePath1, "abcd.png").toString();

        try {
            // Create the directory if it doesn't exist
           // Files.createDirectories(Paths.get(directoryPath));
            byte[] decodedBytes = Base64.decodeBase64(screenshot);
            // Write the byte array to the new image file
            Files.write(Paths.get(filePath), decodedBytes);

            System.out.println("Byte array has been written to the new image file successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    byte[] readImage(String path) {
        // Specify the file path of the image
        byte[] imageByteArray = null;
        try {
            // Read the image file into a byte array
            imageByteArray = Base64.encodeBase64(Files.readAllBytes(Paths.get(path)));

            // Use the imageByteArray as needed (e.g., process or display the image)

            System.out.println("Image file has been read into a byte array successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageByteArray;
    }


}
