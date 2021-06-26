package FS02;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class step3Controller {

    @FXML
    private Button send;

    @FXML
    private Button receive;

    @FXML
    void Send(ActionEvent event) throws IOException {
        Main.setRoot("Send");
    }

    @FXML
    void receiveBtn(ActionEvent event) throws IOException {
         Main.setRoot("Receive");
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

