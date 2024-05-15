import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResizeImage {
    public static void resizeImage(String inputImagePath, String outputImagePath, int newWidth, int newHeight, String fileFormat)
            throws IOException {
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);

        // Calculate the aspect ratio
        double aspectRatio = (double) inputImage.getWidth() / inputImage.getHeight();

        // Calculate the new height if newWidth is specified
        if (newWidth > 0) {
            newHeight = (int) (newWidth / aspectRatio);
        } else if (newHeight > 0) {
            newWidth = (int) (newHeight * aspectRatio);
        }

        // Create a new BufferedImage with the desired width and height
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, inputImage.getType());
        Graphics2D g2d = resizedImage.createGraphics();

        // Apply antialiasing for better image quality
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(inputImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();

        // Write the resized image to the specified output file
        ImageIO.write(resizedImage, fileFormat, new File(outputImagePath));
    }
}
