package back;

import javafx.scene.image.*;

import java.io.File;

public class MosaicBuilder{

    //private Piece[] pieces;
    private TileList tileList;
    private int tileSizeX = 10;
    private int tileSizeY = 10;
    private Mosaic mosaic;

    public MosaicBuilder(File tileDir) {
        tileList = new TileList();
        tileList.initalize(tileDir);
        tileList.sort();
        /*
        File[] dirList = tileDir.listFiles();

        pieces = new Piece[dirList.length];
        Image temp;
        for (int i = 0; i < dirList.length; i++) {
            temp = new Image(dirList[i].toURI().toString());
            pieces[i] = new Piece(temp);
        }

        TileSorter.sort(pieces);*/
    }

    public void setImage(Image image){
        mosaic.setImage(image);
    }
    public Image run(){
        //int imageWidth = mosaic.getWidth();
        //int imageHeight = mosaic.getHeight();
        //Image[][] mosaicArr = new Image[imageWidth/tileSizeX][imageHeight/tileSizeY];
        //initializeMosaicArr(mosaic, mosaicArr, imageWidth, imageHeight);
        initializeMosaic();
        return mosaic.getCompletedMosaic();
    }


    //private void initializeMosaicArr(Image img, Image[][] mosaicArr, int imageWidth, int imageHeight) {
    private void initializeMosaic() {

        mosaic.allocateTiles(tileSizeX, tileSizeY, tileList);

    }

    public void setTileSize(int tileSizeX, int tileSizeY){
        this.tileSizeX = tileSizeX;
        this.tileSizeY = tileSizeY;
    }
}
