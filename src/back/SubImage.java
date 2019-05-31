package back;


import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

//public class SubImage implements Comparable{

public class SubImage{
    int originX, originY, boundX, boundY;

    PixelReader reference;

    public SubImage(PixelReader reference, int originX, int originY, int boundX, int boundY){
        this.reference = reference;
        this.originX = originX;
        this.originY = originY;
        this.boundX = boundX;
        this.boundY = boundY;
        init();
    }

    private void init(){
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
        return reference.getColor(x, y).getHue();
    }

    private double getSaturation(int x, int y){
        return reference.getColor(x, y).getSaturation();
    }

    private double getBrightness(int x, int y){
        return reference.getColor(x, y).getBrightness();
    }

}
