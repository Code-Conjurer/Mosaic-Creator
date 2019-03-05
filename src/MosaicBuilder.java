import javafx.scene.image.Image;

import java.io.File;

public class MosaicBuilder {

    private Piece[] pieces;
    private int tileSizeX;
    private int tileSizeY;

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

    //public void setTiles(File tileDir){

    public void setTileSize(int tileSizeX, int tileSizeY){
        this.tileSizeX = tileSizeX;
        this.tileSizeY = tileSizeY;
    }
}
