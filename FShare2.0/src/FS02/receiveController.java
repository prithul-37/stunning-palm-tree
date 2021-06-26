package FS02;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class receiveController implements Initializable {

    public static String xx = "00 %";
    public static Socket thisSocket = null;
    InputStream IS = null;
    DataInputStream DIS = null;
    String selectedFileName,filePath,fullFileNameWithPath = null;
    long selectedFileSize = 0;
    File receiveFile = null;
    public static float x1 = 0;
    String defaultFilePath = "C:\\Users\\prith\\Desktop";

    @FXML
    private TextField fileName;

    @FXML
    private TextField fileSize;

    @FXML
    private TextField showSetPath;

    @FXML
    private Text status;

    @FXML
    private Button setPath;

    @FXML
    private Button receive;

    @FXML
    private Button EXIT;

    @FXML
    private Button send;

    @FXML
    private Button receiveAgain;

    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void receiveAgainBtn(ActionEvent event) throws IOException {
        Main.setRoot("Receive");

    }

    @FXML
    private ProgressBar progressBar;

    @FXML
    void receiveBtn(ActionEvent event) throws IOException {
        if(filePath != null) fullFileNameWithPath = filePath + "\\" + selectedFileName;
        else fullFileNameWithPath = defaultFilePath + "\\" + selectedFileName;
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(.1),
                new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                               //System.out.println("Calling...!");
                               progressBar.setProgress(x1);
                               status.setText("Progress : "+xx);
                 }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // FileHandel.receiveFile(thisSocket,fullFileNameWithPath,selectedFileSize);
        Thread rf = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileHandel.receiveFile(thisSocket,fullFileNameWithPath,selectedFileSize);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        rf.start();

    }

    @FXML
    void sendBtn(ActionEvent event) throws IOException {
        Main.setRoot("Send");
    }

    @FXML
    void setPathBtn(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Path to Store File");
        directoryChooser.setInitialDirectory(new File("C:\\Users\\prith\\Desktop"));
        receiveFile = directoryChooser.showDialog(null);
        filePath = receiveFile.getPath();
        showSetPath.setText(filePath);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        x1 = 0;
        xx = "00 %";
        try {
            IS = thisSocket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DIS = new DataInputStream(IS);
        Thread fileInfoReceive = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean x = true;
                while (x){
                    try {
                        selectedFileName = DIS.readUTF();
                        selectedFileSize = DIS.readLong();
                        if(selectedFileSize != 0 && selectedFileName != null){
                            fileName.setText(selectedFileName);
                            if (selectedFileSize/1024 <=1000){
                                fileSize.setText(String.valueOf(selectedFileSize/1024)+" KB");
                            }
                            else{
                                fileSize.setText(String.valueOf(selectedFileSize/(1024*1024))+" MB");
                            }
                            x = false;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        fileInfoReceive.start();

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
