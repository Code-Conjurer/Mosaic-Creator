package back;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageTransformer {

    private ImageTransformer(){}

    public static Image reduce(Image original, int newX, int newY){
        double oldX = original.getWidth();
        double oldY = original.getHeight();

        int xRatio = (int) (oldX/newX);
        int yRatio = (int) (oldY/newY);
        PixelReader reader = original.getPixelReader();
        WritableImage writableImage = new WritableImage(newX, newY);
        PixelWriter writer = writableImage.getPixelWriter();
        Color c;
        for(int x = 0; x < newX; x++){
            for(int y = 0; y < newY; y++){
                c = reader.getColor(xRatio*x, yRatio*y);
                writer.setColor(x,y,c);
            }
        }

        return (Image) writableImage;
    }
}
