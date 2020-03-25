package
utils;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;

public class ImageManager {
    public static Image readImage(String imagePath) {
        try (FileInputStream inputImageStream = new FileInputStream(imagePath)) {
            return new Image(inputImageStream);
        } catch (FileNotFoundException noImage) {
            throw new IllegalArgumentException("No such image on path: " + imagePath);
        } catch (IOException ioEx) {
            throw new UncheckedIOException(ioEx);
        }
    }
}