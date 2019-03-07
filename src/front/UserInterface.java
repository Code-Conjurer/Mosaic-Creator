package front;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;

public class UserInterface extends Application {

    public Mosaic mosaic;

    @Override
    public void start(Stage primaryStage) throws Exception{
        mosaic = new Mosaic();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ui.fxml"));
        loader.setController(mosaic.getController());
        //((Controller) loader.getController()).setMosaic(mosaic);
        //((Controller) loader.getController()).setupEvents();

        Parent root = loader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1400, 900));
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
