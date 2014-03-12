package boboPOM.controller;

import boboPOM.util.SocketLink;
import boboPOM.util.BroadcastSessionUtil;
import boboPOM.util.Config;
import boboPOM.util.MsgQueue;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jeremie on 14-3-1.
 */
public class ControllerTest implements Initializable {

    private String resourcesPath;
    @FXML
    private ImageView underBackgroundLeft;
    @FXML
    private ImageView underBackgroundRight;
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
    @FXML
    private Button findBroadcast;

    private Timeline broadcaseTimeline;
    private BroadcastSessionUtil broadcastSessionUtil;
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
        broadcaseTimeline.play();
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
        if(socketLink.getIp() == null)
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
     *
     * @param event
     * @throws IOException
     */

    @FXML
    private void findBroadcast(ActionEvent event) throws IOException {
        String receive = broadcastSessionUtil.receiveBroadcast();
        if(receive!=null){
            key.send("找到服务器：" + receive + "    ");
            socketLink.setIp(receive);
            this.connectSocket(event);
        }
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
                if (!msgQueue.isEmpty()) {
                    String msg = msgQueue.recv();
                    serverStatus.setText(msg);
                    if("连接成功".equals(msg)){
                        broadcaseTimeline.stop();
                    }
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

        broadcastSessionUtil = new BroadcastSessionUtil(Config.PORT);
        broadcaseTimeline = new Timeline();
        broadcaseTimeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame broadcaseFame = new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                broadcastSessionUtil.sendBroadcast();
                System.out.println("-------->");
            }
        });
        broadcaseTimeline.getKeyFrames().add(broadcaseFame);

        socketLink = new SocketLink(msgQueue, key, Config.PORT);
    }

    private void initializeModule() {
        Image image = new Image(resourcesPath + "/images/background.png", Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, true, false);
        Image imagetest = new Image(resourcesPath + "/images/number/lineup1.png", 15, 15, true, false);
        Image underBackgroundLeftImage = new Image(resourcesPath + "/images/underBackground.png");
        underBackgroundLeft.setImage(underBackgroundLeftImage);
        underBackgroundRight.setImage(underBackgroundLeftImage);
        underBackgroundLeft.setLayoutX(48);
        underBackgroundLeft.setLayoutY(-8);
        underBackgroundRight.setLayoutX(526);
        underBackgroundRight.setLayoutY(-8);
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
        findBroadcast.setLayoutX(190);
        findBroadcast.setLayoutY(90);
        clientStatus.setLayoutX(20);
        clientStatus.setLayoutY(130);
        testImage.setImage(imagetest);
        testImage.setLayoutX(Config.SCREEN_WIDTH / 4 - 100);
        testImage.setLayoutY(Config.SCREEN_HEIGHT / 2);
    }

    private void moveImage() {
        double x = testImage.getLayoutX();
        double y = testImage.getLayoutY();
        double speed = 10;
        if ((x >= Config.SCREEN_WIDTH / 4 - 100 && x < Config.SCREEN_WIDTH / 4) && (y >= Config.SCREEN_HEIGHT / 2 && y < Config.SCREEN_HEIGHT / 2 + 100)) {
            testImage.setLayoutX(x + speed);
            testImage.setLayoutY(y + speed);
        } else if ((x >= Config.SCREEN_WIDTH / 4 && x < Config.SCREEN_WIDTH / 4 + 100) && (y <= Config.SCREEN_HEIGHT / 2 + 100 && y > Config.SCREEN_HEIGHT / 2)) {
            testImage.setLayoutX(x + speed);
            testImage.setLayoutY(y - speed);
        } else if ((x > Config.SCREEN_WIDTH / 4 && x <= Config.SCREEN_WIDTH / 4 + 100) && (y <= Config.SCREEN_HEIGHT / 2 && y > Config.SCREEN_HEIGHT / 2 - 100)) {
            testImage.setLayoutX(x - speed);
            testImage.setLayoutY(y - speed);
        } else if ((x > Config.SCREEN_WIDTH / 4 - 100 && x <= Config.SCREEN_WIDTH / 4) && (y < Config.SCREEN_HEIGHT / 2 && y >= Config.SCREEN_HEIGHT / 2 - 100)) {
            testImage.setLayoutX(x - speed);
            testImage.setLayoutY(y + speed);
        }
    }

    private void setFilePath(URL location) {
        File file = new File(location.toString());
        this.resourcesPath = file.getParent().replaceAll("\\\\", "/") +"/resources";
    }
}
