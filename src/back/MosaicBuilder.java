package back;

import front.Mosaic;
import javafx.scene.image.*;

import java.io.File;

public class MosaicBuilder{

    final int DEFAULT_TILE_SIZE = 10;
    private TileListBuilder tileListBuilder;
    private int tileSizeX;
    private int tileSizeY;
    private Image image;

    public MosaicBuilder(File tileDir) {
        tileListBuilder = new TileListBuilder();
        tileListBuilder.initialize(tileDir, 2, 2);
        //tileListBuilder.sort();
        /*
        File[] dirList = tileDir.listFiles();

        Tiles = new Tile[dirList.length];
        Image temp;
        for (int i = 0; i < dirList.length; i++) {
            temp = new Image(dirList[i].toURI().toString());
            Tiles[i] = new Tile(temp);
        }

        TileSorter.sort(Tiles);*/
    }

    public void setImage(Image image){
        this.image = image;
    }

    public Image run(Image image, Integer tileSizeX, Integer tileSizeY, int NumOfQuadrantsX, int NumOfQuadrantsY){
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

        return buildMosaic();
    }


    //private void initializeMosaicArr(Image img, Image[][] mosaicArr, int imageWidth, int imageHeight) {
    private Image buildMosaic() {

        int numberOfTilesX = ((int) image.getWidth()) / tileSizeX;
        int numberOfTilesY = ((int) image.getHeight()) / tileSizeY;

        Image[][] mosaicArr = new Image[numberOfTilesX][numberOfTilesY];
        PixelReader pr = image.getPixelReader();
        Thread[] tileBuilders = new TileBuilder[numberOfTilesX * numberOfTilesY];
        int index;

        for (int x = 0; x < numberOfTilesX; x++) {
            for (int y = 0; y < numberOfTilesY; y++) {
                System.out.println(Runtime.getRuntime().availableProcessors());
                while(Runtime.getRuntime().availableProcessors() == 0){
                    Thread.yield();
                }

                index = (numberOfTilesY * x) + y;

                tileBuilders[index] = new TileBuilder(
                        x * tileSizeX, y * tileSizeY, tileSizeX, tileSizeY,
                        tileListBuilder.getTiles(), mosaicArr, pr);

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

        int imageWidth = (int) image.getWidth();
        int imageHeight = (int) image.getHeight();

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

        return (Image) writableMosaic;
    }

    public void setTileSize(int tileSizeX, int tileSizeY){
        this.tileSizeX = tileSizeX;
        this.tileSizeY = tileSizeY;
    }
}
