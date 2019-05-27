package back;

import javafx.scene.image.*;

import java.io.File;

public class MosaicBuilder{

    final int DEFAULT_TILE_SIZE = 10;
    private TileList tileList;
    private int tileSizeX = 10;
    private int tileSizeY = 10;
    private Image image;

    public MosaicBuilder(File tileDir) {
        tileList = new TileList();
        tileList.initialize(tileDir);
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

    public Image run(Image image, Integer tileSizeX, Integer tileSizeY, int NumOfQuadrants){
        //int imageWidth = mosaic.getWidth();
        //int imageHeight = mosaic.getHeight();
        //Image[][] mosaicArr = new Image[imageWidth/tileSizeX][imageHeight/tileSizeY];
        //initializeMosaicArr(mosaic, mosaicArr, imageWidth, imageHeight);


        this.image = image;
        if(tileSizeX == null || tileSizeY == null){
            setTileSize(DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE);
        }else{
            setTileSize(tileSizeX.intValue(), tileSizeY.intValue());
        }

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

    /*private Image img;
    private Image completedMosaic;


    public void allocateTiles(int tileSizeX,int tileSizeY,TileList tileList) {

        int numberOfTilesX = ((int) img.getWidth()) / tileSizeX;
        int numberOfTilesY = ((int) img.getHeight()) / tileSizeY;

        PixelReader pr = img.getPixelReader();
        Thread[] tileBuilders = new TileBuilder[numberOfTilesX * numberOfTilesY];
        int index;
        for (int x = 0; x < numberOfTilesX; x++) {
            for (int y = 0; y < numberOfTilesY; y++) {
                index = (numberOfTilesY * x) + y;

                tileBuilders[index] = new TileBuilder(
                        x * tileSizeX, y * tileSizeY, tileSizeX, tileSizeY,
                        pieces, mosaicArr, pr);

                tileBuilders[index].start();
            }
        }
        for(Thread t : tileBuilders) {
            try {
                t.join();
            }catch (Exception e){
                e.printStackTrace();
            }
        }



        WritableImage writableMosaic = new WritableImage(imageWidth, imageHeight);
        Image tempTile;
        PixelReader pixelReader;
        PixelWriter pixelWriter = writableMosaic.getPixelWriter();
        for(int x = 0; x < numberOfTilesX; x++ ){
            for(int y = 0; y < numberOfTilesY; y++){
                tempTile = ImageTransformer.reduce(mosaicArr[x][y], tileSizeX, tileSizeY);
                pixelReader = tempTile.getPixelReader();
                pixelWriter.setPixels(x*tileSizeX, y*tileSizeY, tileSizeX, tileSizeY, pixelReader, 0, 0);

            }
        }

        mosaic = (Image) writableMosaic;
    }

    public void setImage(Image img){
        this.img = img;
    }

    public Image getCompletedMosaic() {
        return completedMosaic;
    }*/
}
