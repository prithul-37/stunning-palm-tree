package FS02;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class step1Controller {

    @FXML
    private Button Host;

    @FXML
    private Button Connect;

    @FXML
    void connect(ActionEvent event) throws IOException {
      System.out.println("connect OK");
      Main.setRoot("Connect");
    }

    @FXML
    void host(ActionEvent event) throws IOException {
        System.out.println("host OK");
        Main.setRoot("Host");
    }

    @FXML
    private Button EXIT;

    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }

    double x,y ;

    @FXML
    void Move(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    void Move1(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

}
