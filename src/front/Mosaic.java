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

    public Mosaic(String... args){
        if(args == null) {
            try {
                mosaicBuilder = new MosaicBuilder(new File(Mosaic.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "/res/tiles/jpg"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("failed to load");
            //mosaicBuilder = new MosaicBuilder(new File(args[0]));
        }

        controller = new Controller();
        userInterface = new UserInterface(this);
        Application.launch(UserInterface.class, args);
    }

    public static void main(String[] args) {
        new Mosaic(args);
    }

    public void setImageFile(File imageFile){
        //this.imageFile = imageFile;
        image = new Image(imageFile.toURI().toString());
        mosaicBuilder.setImage(image);
        controller.showImage(image);

    }

    public Image getImage(){
        return image;
    }

    public void runMosaicBuilder(Integer tileSizeX, Integer tileSizeY){
        if(tileSizeX == null || tileSizeY == null)
            image = mosaicBuilder.run(DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE);
        image = mosaicBuilder.run(tileSizeX, tileSizeY);

        controller.showImage(image);
    }
}
