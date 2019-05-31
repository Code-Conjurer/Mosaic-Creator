package back;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class TileBuilder extends Thread {

    int sourceX; int sourceY; int sizeX; int sizeY;
    ArrayList<Tile> tiles;
    Image[][] mosaicTiles;
    PixelReader pr;

    public TileBuilder(int sourceX, int sourceY, int sizeX, int sizeY, ArrayList<Tile> tiles, Image[][] mosaicTiles, PixelReader pr){
        this.sourceX = sourceX;
        this.sourceY = sourceY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.tiles = tiles;
        this.mosaicTiles = mosaicTiles;
        this.pr = pr;

    }

    @Override
    public void run(){
        int xIndex = sourceX/sizeX;
        int yIndex = sourceY/sizeY;

        SimpleAlg(xIndex, yIndex);
    }

    private void quadrantAverageAlg(int xIndex, int yIndex) {

    }


    private void SimpleAlg(int xIndex, int yIndex){
        if(mosaicTiles[xIndex][yIndex] != null){
            System.out.println("Collision: " + xIndex + " " + yIndex);
            return;
        }

        double red = 0; double green = 0; double blue = 0;
        Color c;
        for(int x = sourceX; x < sourceX + sizeX; x++){
            for(int y = sourceY; y < sourceY + sizeY; y++){
                red += pr.getColor(x,y).getRed();
                green += pr.getColor(x,y).getGreen();
                blue += pr.getColor(x,y).getBlue();
            }
        }
        red /= sizeX*sizeY;
        green /= sizeX*sizeY;
        blue /= sizeX*sizeY;
        c = new Color(red, green, blue, 0);
        int pieceIndex = search(tiles, c);
        mosaicTiles[xIndex][yIndex] = tiles.get(pieceIndex).getImage();
    }

    /*
     * compares the brightness of two colors within the bounds, then
     * uses ColorCheck if similar brightness
     */
    public static int search(ArrayList<Tile> tiles, Color color){
        double brightness = color.getBrightness();
        int low = 0;
        int high = tiles.size() - 1;
        double MARGIN_OF_ERROR = 10.0/256.0; //Currently chosen arbitrarily

        while( high >= low){
            int mid = (low + high) / 2;

            if(Math.abs(tiles.get(mid).getBrightness() - brightness) <= MARGIN_OF_ERROR){
                return colorCheck(tiles, mid, color, MARGIN_OF_ERROR);
            }
            if(tiles.get(mid).getBrightness() < brightness){
                low = mid + 1;
            }
            if(tiles.get(mid).getBrightness() > brightness){
                high = mid - 1;
            }
        }
        System.out.println("broke");
        return -1;

    }

    /*
     * compares the color values and returns if there in the marginOfError
     * recurse if the color is not found in the, by modifying the marginOfError
     */
    public static int colorCheck(ArrayList<Tile> tiles, int index, Color c, double marginOfError){

        double red = (c.getRed());
        double green = (c.getGreen());
        double blue = (c.getBlue());

        for(int i = index; i < tiles.size(); i++){
            double tempRed = tiles.get(i).getRed();
            double tempGreen = tiles.get(i).getGreen();
            double tempBlue = tiles.get(i).getBlue();

            if(Math.abs(tempRed - red) <= marginOfError
                    && Math.abs(tempGreen - green) <= marginOfError
                    && Math.abs(tempBlue - blue) <= marginOfError){
                return i;
            }

        }
        for(int j = index; j > 0; j--){
            double tempRed = tiles.get(j).getRed();
            double tempGreen = tiles.get(j).getGreen();
            double tempBlue = tiles.get(j).getBlue();

            if(Math.abs(tempRed - red) <= marginOfError
                    && Math.abs(tempGreen - green) <= marginOfError
                    && Math.abs(tempBlue - blue) <= marginOfError){
                return j;
            }

        }
        return colorCheck(tiles,index, c, marginOfError + (marginOfError / 10)); //TODO: define the increment for the marginOfError
    }

}
