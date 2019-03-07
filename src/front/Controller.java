package front;


import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import back.MosaicBuilder;

import javax.imageio.ImageIO;

public class Controller {

    @FXML
    private MenuItem openFileImage;
    @FXML
    private MenuItem saveFileImage;
    @FXML
    private TextField tileWidthSetting;
    @FXML
    private TextField tileHeightSetting;
    @FXML
    private Button startButton;
    @FXML
    private ImageView imageView;

    private FileChooser fileChooser;
    //private UserInterface userInterface;
    private Mosaic mosaic;

    public void setMosaic(Mosaic mosaic){
        this.mosaic = mosaic;
    }

    public void initialize() {
        fileChooser = new FileChooser();
        //this.userInterface = userInterface;
        //this.mosaic = mosaic;
        setupEvents();

    }

    private void setupEvents(){

        openFileImage.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                //Stage tempStage = new Stage();
                //tempStage.setTitle("Open Image");
                fileChooser.setTitle("Open Image");
                File imageFile = fileChooser.showOpenDialog(new Stage());
                mosaic.setImageFile(imageFile);
            }
        });

        saveFileImage.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                saveFileImageEvent();
            }
        });

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                setStartButtonEvent();
            }
        });
    }

    private void saveFileImageEvent(){
        fileChooser.setTitle("Save Image");
        File saveImageFile = fileChooser.showSaveDialog(new Stage());
        if(saveImageFile != null){
            try{
                ImageIO.write(SwingFXUtils.fromFXImage(mosaic.getImage(),
                        null), "png", saveImageFile);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void setStartButtonEvent(){
        if(imageView.getImage() == null)
            return;
        String tileSizeXStr = tileWidthSetting.getText();
        String tileSizeYStr = tileHeightSetting.getText();
        Integer tileSizeX = null;
        Integer tileSizeY = null;
        try{
            tileSizeX = Integer.parseInt(tileSizeXStr);
            tileSizeY = Integer.parseInt(tileSizeYStr);
        }catch (Exception e){
            e.printStackTrace();
        }
        mosaic.runMosaicBuilder(tileSizeX, tileSizeY);
    }



    public void showImage(Image image){
        imageView.setImage(image);
    }
}
