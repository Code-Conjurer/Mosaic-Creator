package front;

import back.MosaicBuilder;
import javafx.application.Application;
import javafx.scene.image.Image;

import javax.xml.bind.annotation.XmlType;
import java.io.File;

public class Mosaic {

    final int DEFAULT_TILE_SIZE = 10;
    //File imageFile;
    Image image;
    MosaicBuilder mosaicBuilder;
    Controller controller;
    UserInterface userInterface;

    public Mosaic(){
        try {
            mosaicBuilder = new MosaicBuilder(new File(Mosaic.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "/res/tiles/jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        controller = new Controller();
        controller.setMosaic(this);
    }

    public void setUserInterface(UserInterface userInterface){
        this.userInterface = userInterface;
    }

    public void setImageFile(File imageFile){
        //this.imageFile = imageFile;
        image = new Image(imageFile.toURI().toString());
        mosaicBuilder.setImage(image);
        controller.showImage(image);

    }

    public Controller getController(){
        return controller;
    }
    public Image getImage(){
        return image;
    }

    public void runMosaicBuilder(Integer tileSizeX, Integer tileSizeY){
        if(tileSizeX == null || tileSizeY == null){
            mosaicBuilder.setTileSize(DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE);
        }else{
            mosaicBuilder.setTileSize(tileSizeX.intValue(), tileSizeY.intValue());
        }

        image = mosaicBuilder.run();
        System.out.println("done");
        controller.showImage(image);
    }
}
