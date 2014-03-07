package boboPOM.controller;

import boboPOM.service.SocketLink;
import boboPOM.util.Config;
import boboPOM.util.MsgQueue;
import boboPOM.view.BackgroundView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jeremie on 14-3-5.
 */
public class Controller implements Initializable {

    private String resourcesPath;
    @FXML
    private ImageView underBackgroundLeft;
    @FXML
    private ImageView underBackgroundRight;
    @FXML
    private BackgroundView background;


    private Timeline timeline;
    private MsgQueue<String> msgQueue;
    private MsgQueue<String> key;
    private SocketLink socketLink;
    private Thread tClient;
    private Thread tServer;

    /**
     * @param event
     * @throws java.io.IOException
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
        this.setFilePath(location);
        initializeModule();
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(Config.ANIMATION_TIME, new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            }
        });
        timeline.getKeyFrames().add(kf);
        timeline.play();
        msgQueue = new MsgQueue<String>();
        key = new MsgQueue<String>();
        socketLink = new SocketLink(msgQueue, key, 5918);
    }

    private void initializeModule() {
        Image image = new Image(resourcesPath + "/images/background.png", Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, true, false);
        Image underBackgroundLeftImage = new Image(resourcesPath + "/images/underBackground.png");
        underBackgroundLeft.setImage(underBackgroundLeftImage);
        underBackgroundRight.setImage(underBackgroundLeftImage);
        underBackgroundLeft.setLayoutX(48);
        underBackgroundLeft.setLayoutY(-8);
        underBackgroundRight.setLayoutX(526);
        underBackgroundRight.setLayoutY(-8);
        background.setBackgroundImage(image);
        background.setVisible(true);
    }

    private void setFilePath(URL location) {
        File file = new File(location.toString());
        this.resourcesPath = file.getParent().replaceAll("\\\\", "/") +"/resources";
    }
}
