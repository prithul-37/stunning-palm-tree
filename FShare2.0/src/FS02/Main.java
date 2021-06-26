package FS02;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.IOException;

public class Main extends Application {

    static Stage primary;
    @Override
    public void start(Stage stage) throws IOException {
        primary = stage;
        stage.setTitle("FShare");
        Image image = new Image("/Icon/Logo(SkyBlue).png");
        stage.getIcons().add(image);
        Scene scene = new Scene(loadFXML("StartWindow"));
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {

        primary.setScene(new Scene(loadFXML(fxml)));
        //scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }


    /*public static void main(String[] args) { launch(args);
    }*/
}
