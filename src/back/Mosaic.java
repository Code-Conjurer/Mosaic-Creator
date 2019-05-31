package back;

/*import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class Mosaic {
    /*private Image img;
    private Image completedMosaic;


    public void allocateTiles(int tileSizeX,int tileSizeY,TileListBuilder tileList) {

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
                        Tiles, mosaicArr, pr);

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
    }
}*/
