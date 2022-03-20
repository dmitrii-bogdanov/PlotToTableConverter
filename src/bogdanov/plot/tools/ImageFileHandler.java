package bogdanov.plot.tools;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

public class ImageFileHandler {
    public static BufferedImage load(File file) throws IOException {
        System.out.println("Image is LOADED");
        return ImageIO.read(file);
    }

    public static void save(BufferedImage image, File file) throws IOException {
        ImageIO.write(image, file.getName().substring(file.getName().lastIndexOf('.') + 1), file);
    }
}
