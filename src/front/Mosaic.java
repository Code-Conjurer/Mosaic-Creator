package front;

import back.MosaicBuilder;
import javafx.scene.image.Image;
import java.io.File;

public class Mosaic {

    //File imageFile;
    private Image image;
    private MosaicBuilder mosaicBuilder;
    private Controller controller;
    private UserInterface userInterface;

    public Mosaic(){
        try {
            mosaicBuilder = new MosaicBuilder(new File(Mosaic.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "/res/tiles/jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        controller = new Controller(this);
        //controller.setMosaic(this);
    }

    public void setUserInterface(UserInterface userInterface){
        this.userInterface = userInterface;
    }

    public void setImageFile(File imageFile){
        //this.imageFile = imageFile;
        image = new Image(imageFile.toURI().toString());
        //mosaicBuilder.setImage(image);
        controller.showImage(image);

    }

    public Controller getController(){
        return controller;
    }
    public Image getImage(){
        return image;
    }

    public void runMosaicBuilder(Integer tileSizeX, Integer tileSizeY, int NumOfQuadrantsX, int NumOfQuadrantsY){

        if (image == null) {
            System.out.println("Null Image");
            return;
        }

        image = mosaicBuilder.run(image, tileSizeX, tileSizeY, NumOfQuadrantsX, NumOfQuadrantsY);
        System.out.println("done");
        controller.showImage(image);
    }
}
