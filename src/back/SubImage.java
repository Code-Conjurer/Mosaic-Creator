package back;


import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

public class SubImage implements Comparable{
    int originX, originY, boundX, boundY;
    PixelReader reference;

    public SubImage(PixelReader reference, int originX, int originY, int boundX, int boundY){
        this.reference = reference;
        this.originX = originX;
        this.originY = originY;
        this.boundX = boundX;
        this.boundY = boundY;
    }

    private void foo(){
        double hue = 0;
        double saturation = 0;
        double brightness = 0;

        for(int x = originX; x < boundX; x++){
            for(int y = originY; y < boundY; y++){
                hue += getHue(x , y);
                saturation += getSaturation(x , y);
                brightness += getBrightness(x, y);
            }
        }

        int numberOfPixels = (boundX - originX)*(boundY - originY);
        hue /= numberOfPixels;
        saturation /= numberOfPixels;
        brightness /= numberOfPixels;


    }

    private double getHue(int x, int y){

    }

    private double getSaturation(int x, int y){

    }

    private double getBrightness(int x, int y){

    }

    @Override
    public int compareTo(Object o) {
        if(!(o instanceof SubImage))
            return 0;
        return 1;
    }
}
