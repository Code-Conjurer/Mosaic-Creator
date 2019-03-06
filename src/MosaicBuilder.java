import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

import java.io.File;

public class MosaicBuilder {

    private Piece[] pieces;
    private int tileSizeX;
    private int tileSizeY;
    private Image mosaic;

    public MosaicBuilder(int tileSizeX, int tileSizeY, File tileDir) {
        this.tileSizeX = tileSizeX;
        this.tileSizeY = tileSizeY;

        File[] dirList = tileDir.listFiles();

        pieces = new Piece[dirList.length];
        Image temp;
        for (int i = 0; i < dirList.length; i++) {
            temp = new Image(dirList[i].toURI().toString());
            pieces[i] = new Piece(temp);
        }

        TileSorter.sort(pieces);
    }

    public void run(File image){

        Image img = new Image(image.toURI().toString());
        int imageWidth = (int) img.getWidth();
        int imageHeight = (int) img.getHeight();
        Image[][] mosaicArr = new Image[imageWidth/tileSizeX][imageHeight/tileSizeY];
        initializeMosaicArr(img, mosaicArr, imageWidth, imageHeight);

    }


    private void initializeMosaicArr(Image img, Image[][] mosaicArr, int imageWidth, int imageHeight) {
        PixelReader pr = img.getPixelReader();
        int numberOfTilesX = imageWidth / tileSizeX;
        int numberOfTilesY = imageHeight / tileSizeY;
        
        Thread[] tileBuilders = new TileBuilder[numberOfTilesX * numberOfTilesY];

        int index;
        for (int x = 0; x < numberOfTilesX; x++) {
            for (int y = 0; y < numberOfTilesY; y++) {
                index = (numberOfTilesX * x) + y;

                tileBuilders[index] = new TileBuilder(
                        x * tileSizeX, y * tileSizeY, tileSizeX, tileSizeY,
                        pieces, mosaicArr, pr);

                tileBuilders[index].start();
            }
        }
    }

    private Image getMosaic(){
        return mosaic;
    }
    //public void setTiles(File tileDir){

    public void setTileSize(int tileSizeX, int tileSizeY){
        this.tileSizeX = tileSizeX;
        this.tileSizeY = tileSizeY;
    }
}
