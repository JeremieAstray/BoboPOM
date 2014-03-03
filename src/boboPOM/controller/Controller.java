package boboPOM.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;
import boboPOM.service.SocketLink;
import boboPOM.util.MsgQueue;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jeremie on 14-3-1.
 */
public class Controller implements Initializable {

    final Duration ANIMATION_TIME = Duration.millis(40);
    final double width = 960.0;
    final double height = 540.0;

    @FXML
    private ImageView background;
    @FXML
    private TextField textField;
    @FXML
    private Text connectStatus;
    @FXML
    private Text serverStatus;
    @FXML
    private Button openServer;
    @FXML
    private Button closeServer;
    @FXML
    private Button openClient;
    @FXML
    private Button closeClient;
    @FXML
    private Text clientStatus;
    @FXML
    private ImageView testImage;

    private Timeline timeline;
    private MsgQueue<String> msgQueue;
    private MsgQueue<String> key;
    private SocketLink socketLink;
    private Thread tClient;
    private Thread tServer;

    /**
     * @param event
     * @throws IOException
     */
    @FXML
    private void click(KeyEvent event) throws IOException {
        //String get = event.getText();
        String get = event.getCode().getName();
        if (socketLink != null && socketLink.isLinked()) {
            socketLink.send(get);
        }
    }

    /**
     * 打开socket服务
     *
     * @param event
     * @throws IOException
     * @throws InterruptedException
     */
    @FXML
    private void openSocketServer(ActionEvent event) throws IOException, InterruptedException {
        socketLink.setServer(true);
        tClient = null;
        tServer = new Thread(socketLink);
        tServer.start();
    }

    /**
     * 关闭socket服务
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void closeSocketServer(ActionEvent event) throws IOException {
        if (tServer != null)
            if (socketLink.cancel(true))
                tServer.interrupt();
    }

    /**
     * 连接socket
     *
     * @param event
     */
    @FXML
    private void connectSocket(ActionEvent event) {
        socketLink.setServer(false);
        socketLink.setIp("localhost");
        tServer = null;
        tClient = new Thread(socketLink);
        tClient.start();
    }

    /**
     * 断开socket连接
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void disConnectSocket(ActionEvent event) throws IOException {
        if (tClient != null)
            if (socketLink.cancel(true))
                tClient.interrupt();
    }


    /**
     * 初始化controller
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeModule(location);
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(ANIMATION_TIME, new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (!msgQueue.isEmpty()) {
                    serverStatus.setText(msgQueue.recv());
                }
                if (!key.isEmpty()) {
                    clientStatus.setText(clientStatus.getText() + key.recv());
                }
                moveImage();

            }
        });
        timeline.getKeyFrames().add(kf);
        timeline.play();
        msgQueue = new MsgQueue<String>();
        key = new MsgQueue<String>();
        socketLink = new SocketLink(msgQueue, key, 5918);
    }

    private void initializeModule(URL location) {
        Image image = new Image(getFilePath(location) + "/resources/images/main.png", width, height, true, false);
        background.setImage(image);
        background.setVisible(true);
        textField.setLayoutX(10);
        textField.setLayoutY(10);
        connectStatus.setLayoutX(20);
        connectStatus.setLayoutY(50);
        serverStatus.setLayoutX(100);
        serverStatus.setLayoutY(50);
        openServer.setLayoutX(10);
        openServer.setLayoutY(60);
        closeServer.setLayoutX(100);
        closeServer.setLayoutY(60);
        openClient.setLayoutX(10);
        openClient.setLayoutY(90);
        closeClient.setLayoutX(100);
        closeClient.setLayoutY(90);
        clientStatus.setLayoutX(20);
        clientStatus.setLayoutY(130);
        Image imagetest = new Image(getFilePath(location) + "/resources/images/mq0000.png", 15, 15, true, false);
        testImage.setImage(imagetest);
        testImage.setLayoutX(width / 4 - 100);
        testImage.setLayoutY(height / 2);
    }

    private void moveImage() {
        double x = testImage.getLayoutX();
        double y = testImage.getLayoutY();
        double speed = 10;
        if ((x >= width / 4 - 100 && x < width / 4) && (y >= height / 2 && y < height / 2 + 100)) {
            testImage.setLayoutX(x + speed);
            testImage.setLayoutY(y + speed);
        } else if ((x >= width / 4 && x < width / 4 + 100) && (y <= height / 2 + 100 && y > height / 2)) {
            testImage.setLayoutX(x + speed);
            testImage.setLayoutY(y - speed);
        }else if((x > width / 4 && x <= width / 4 + 100) && (y <= height / 2 && y > height / 2 - 100)){
            testImage.setLayoutX(x - speed);
            testImage.setLayoutY(y - speed);
        }else if((x > width / 4 - 100 && x <= width / 4) && (y < height / 2 && y >= height / 2 - 100)) {
            testImage.setLayoutX(x - speed);
            testImage.setLayoutY(y + speed);
        }
    }

    private String getFilePath(URL location) {
        File file = new File(location.toString());
        return file.getParent().replaceAll("\\\\", "/");
    }
}
