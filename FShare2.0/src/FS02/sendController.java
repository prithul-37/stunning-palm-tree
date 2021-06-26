package FS02;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class sendController implements Initializable {

    public static Socket thisSocket ;
    DataOutputStream dataOutputStream = null;
    File fileToSend = null;
    OutputStream OS = null;
    public static String xx = null;

    @FXML
    private Button select;

    @FXML
    private TextField fileSize;

    @FXML
    private Button send;

    @FXML
    private TextField fileName;

    @FXML
    private Button sendAgain;

    @FXML
    private Button Receive;

    @FXML
    private Text status;

    @FXML
    private Button sendInfo;

    @FXML
    void dragDroped(DragEvent event) {
        List<File> files = event.getDragboard().getFiles();
        System.out.println(files.get(0));
        select.setDisable(true);
        fileToSend = files.get(0);
        fileName.setText(fileToSend.getName());
        long size = fileToSend.length();
        if (size/1024 <=1000){
            fileSize.setText(String.valueOf(size/1024)+" KB");
        }
        else{
            fileSize.setText(String.valueOf(size/(1024*1024))+" MB");
        }
        status.setText("File selected");

    }

    @FXML
    void dragOver(DragEvent event) {
        if(event.getDragboard().hasFiles()){
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    void receiveBtn(ActionEvent event) throws IOException {
        xx=null;
        Main.setRoot("Receive");
    }

    @FXML
    void selectBtm(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        fileToSend = fileChooser.showOpenDialog(null);
        fileName.setText(fileToSend.getName());
        long size = fileToSend.length();
        if (size/1024 <=1000){
            fileSize.setText(String.valueOf(size/1024)+" KB");
        }
        else{
            fileSize.setText(String.valueOf(size/(1024*1024))+" MB");
        }
        status.setText("File selected");
    }

    @FXML
    void sendAgainBtn(ActionEvent event) throws IOException {
        xx=null;
        Main.setRoot("Send");
    }

    @FXML
    void sendBtn(ActionEvent event) throws IOException {
        System.out.println(thisSocket);
        System.out.println("Sending file");

           //Timeline function to see progress
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(.1),
                            new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    //System.out.println("Calling...!");
                                    status.setText(xx);
                                }
                            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();

            Thread sf = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        FileHandel.sendFile(thisSocket,fileToSend);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            sf.start();

        //FileHandel.sendFile(thisSocket,fileToSend);
        if(xx.equals("File Sent..!")){
            status.setText(xx);
            timeline.stop();
        }

    }

    @FXML
    void sendInfo(ActionEvent event) throws IOException {
        OS = thisSocket.getOutputStream();
        dataOutputStream = new DataOutputStream(OS);
        dataOutputStream.writeUTF(fileToSend.getName());
        dataOutputStream.writeLong(fileToSend.length());
        dataOutputStream.flush();
        send.setDisable(false);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         status.setText("Drag & Drop or Press Select File");
         send.setDisable(true);
    }
}
