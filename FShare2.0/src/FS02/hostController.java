package FS02;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class hostController implements Initializable {

    ServerSocket serverSocket = null;
    static Socket socket = null;

    @FXML
    private TextField showIP;

    @FXML
    private TextField showPort;

    @FXML
    private Text confirm;

    @FXML
    private Button prepServer;

    @FXML
    private Button cont;

    @FXML
    void cont(ActionEvent event) throws IOException {
        receiveController.thisSocket = socket;
        sendController.thisSocket = socket;
        Main.setRoot("step3");
    }

    @FXML
    void prepServer(ActionEvent event) {
       Thread startSock = new Thread(new Runnable() {
           @Override
           public void run() {
               try {
                   serverSocket = new ServerSocket(0);
                   String IP = new String(String.valueOf(InetAddress.getLocalHost()));
                   showIP.setText(IP.substring(16));
                   showPort.setText(String.valueOf(serverSocket.getLocalPort()));
                   System.out.println("Waiting...");
                   confirm.setText("Server hosted");
                   confirm.setText("Waiting..!");
                   socket = serverSocket.accept();
                   System.out.println(socket);
                   System.out.println("Connected");
                   confirm.setText("Connected");
                   cont.setDisable(false);
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       });
       startSock.start();
       prepServer.setDisable(true);
    }

    @FXML
    private Button EXIT;

    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }

    public void btnManager(){
        cont.setDisable(true);
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        confirm.setText("Press 'Prepare server' to host");
    }
}
