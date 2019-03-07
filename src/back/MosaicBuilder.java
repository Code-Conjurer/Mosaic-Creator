package back;

import javafx.scene.image.*;

import java.io.File;

public class MosaicBuilder{

    private Piece[] pieces;
    private int tileSizeX = 10;
    private int tileSizeY = 10;
    private Image mosaic;

    public MosaicBuilder(File tileDir) {

        File[] dirList = tileDir.listFiles();

        pieces = new Piece[dirList.length];
        Image temp;
        for (int i = 0; i < dirList.length; i++) {
            temp = new Image(dirList[i].toURI().toString());
            pieces[i] = new Piece(temp);
        }

        TileSorter.sort(pieces);
    }

    public void setImage(Image image){
        mosaic = image;
    }
    public Image run(){
        //this.tileSizeX = tileSizeX;
        //this.tileSizeY = tileSizeY;
        //Image img = new Image(imageFile.toURI().toString());
        int imageWidth = (int) mosaic.getWidth();
        int imageHeight = (int) mosaic.getHeight();
        Image[][] mosaicArr = new Image[imageWidth/tileSizeX][imageHeight/tileSizeY];
        initializeMosaicArr(mosaic, mosaicArr, imageWidth, imageHeight);
        return mosaic;
    }


    private void initializeMosaicArr(Image img, Image[][] mosaicArr, int imageWidth, int imageHeight) {
        PixelReader pr = img.getPixelReader();
        int numberOfTilesX = imageWidth / tileSizeX;
        int numberOfTilesY = imageHeight / tileSizeY;

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

				/*ImageView tile = new ImageView();
				tile.setImage(imageList[x][y]);
				tile.setFitHeight(tileSizeY);
				tile.setFitWidth(tileSizeX);
				gp.add(tile, x, y);*/
            }
        }

        mosaic = (Image) writableMosaic;
    }

    //public void setTiles(File tileDir){

    public void setTileSize(int tileSizeX, int tileSizeY){
        this.tileSizeX = tileSizeX;
        this.tileSizeY = tileSizeY;
    }
}
