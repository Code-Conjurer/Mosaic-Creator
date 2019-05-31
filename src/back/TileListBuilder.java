package back;

import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class TileListBuilder {

    ArrayList<Tile> tiles;
    ArrayList<SubTile>[][] subTiles;

    public void initialize(File dir, int quadrantsX, int quadrantsY){

        File[] dirList = dir.listFiles();

        //Tile[] tempPieces = new Tile[dirList.length];
        tiles = new ArrayList(dirList.length);
        subTiles = new ArrayList[quadrantsX][quadrantsY];
        Image temp;
        Tile tempTile;
        SubTile tempSubTile;
        for (int i = 0; i < dirList.length; i++) {

            temp = new Image(dirList[i].toURI().toString());
            tempTile = new Tile(temp, quadrantsX, quadrantsY);
            tiles.add(tempTile);

            for (int x = 0; x < quadrantsX; x++) {
                for (int y = 0; y < quadrantsY; y++) {
                    if (subTiles[x][y] == null)
                        subTiles[x][y] = new ArrayList<>(dirList.length);

                    tempSubTile = new SubTile(tempTile, x, y);
                    subTiles[x][y].add(tempSubTile);
                }
            }
        }
        Collections.sort(tiles);

        for (int x = 0; x < quadrantsX; x++) {
            for (int y = 0; y < quadrantsY; y++) {
                Collections.sort(subTiles[x][y]);
            }
        }
    }

    public ArrayList<Tile> getTiles(){
        return tiles;
    }

    public ArrayList<SubTile>[][] getSubTiles(){
        return subTiles;
    }
}
