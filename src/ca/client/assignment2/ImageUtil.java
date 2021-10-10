package ca.client.assignment2;

import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageUtil {
    private static final String IMAGE_PNG = "png";
    private static final String INITIAL = ",";

    public static BufferedImage fromBaseByteArray(byte[] imageByte){
        BufferedImage image = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static BufferedImage fromBase64(String base64Data){
        String formattedBase64 = getProperBaseData(base64Data);
        BufferedImage image = null;
        try {
            byte[] imageByte;
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(formattedBase64);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static boolean saveImage(BufferedImage image, String fileName, String folderName) {
        try {
            Path path = Paths.get(folderName, fileName);
            ImageIO.write(image, IMAGE_PNG, path.toFile());
            return true;
        } catch(IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String getProperBaseData(String base64Data){
        String formattedBase64 = base64Data;
        if(base64Data.contains(INITIAL)) {
            int index = formattedBase64.indexOf(INITIAL);
            formattedBase64 = base64Data.substring(index+1, formattedBase64.length());
        }
        return formattedBase64;
    }
}
