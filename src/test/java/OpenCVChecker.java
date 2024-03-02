import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.imagecomparison.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.opencv.core.Core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

public class OpenCVChecker {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public void check(AndroidDriver<MobileElement> driver) {

        test(driver);
       /* byte[] screenshot = Base64.encodeBase64(driver.getScreenshotAs(OutputType.BYTES));
        byte[] screenshot2 = Base64.encodeBase64(driver.getScreenshotAs(OutputType.BYTES));
        FeaturesMatchingOptions f = new FeaturesMatchingOptions()
                .withDetectorName(FeatureDetector.ORB)
                .withGoodMatchesFactor(40)
                .withMatchFunc(MatchingFunction.BRUTE_FORCE_HAMMING)
                .withEnabledVisualization();
        FeaturesMatchingResult result = driver
                .matchImagesFeatures(screenshot, screenshot2, f);

        byte[] screenshot3 = Base64.encodeBase64(driver.getScreenshotAs(OutputType.BYTES));
        driver.setSetting(Setting.FIX_IMAGE_FIND_SCREENSHOT_DIMENSIONS, true);
        OccurrenceMatchingResult results = driver
                .findImageOccurrence(screenshot3, screenshot3, new OccurrenceMatchingOptions()
                        .withEnabledVisualization());
        assertNotNull(results.getRect());


        Assert.assertEquals(result.getVisualization().length, 10);
        System.out.println("aasd");
        //System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat m = new Mat(5, 10, CvType.CV_8UC1, new Scalar(0));
        //System.out.println("OpenCV Mat: " + m);
        Mat mr1 = m.row(1);
        mr1.setTo(new Scalar(1));
        Mat mc5 = m.col(5);
        mc5.setTo(new Scalar(5));
        System.out.println("OpenCV Mat data:\n" + m.dump());*/

//        assertThat(result.getVisualization().length, is(greaterThan(0)));
//        assertThat(result.getCount(), is(greaterThan(0)));
//        assertThat(result.getTotalCount(), is(greaterThan(0)));
//        assertFalse(result.getPoints1().isEmpty());
//        assertNotNull(result.getRect1());
//        assertFalse(result.getPoints2().isEmpty());
//        assertNotNull(result.getRect2());

    }

    public void test(AndroidDriver<MobileElement> driver) {
        String baseImagePath="/Users/test/Documents/AppiumTest/appium-sample-test-master/Images/baseimage.png";
        String screenshotPath="/Users/test/Documents/AppiumTest/appium-sample-test-master/Images/";

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

            File opFile = new File("/Users/test/Documents/AppiumTest/appium-sample-test-master/Images/visual_result.png");

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
