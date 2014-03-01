package boboPOM.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;
import boboPOM.service.SocketLink;
import boboPOM.util.MsgQueue;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jeremie on 14-3-1.
 */
public class Controller implements Initializable {
    private Timeline timeline;
    //@FXML
    //private TextField textField;
    @FXML
    private Text serverStatus;
    @FXML
    private Text clientStatus;
    private MsgQueue<String> msgQueue;
    private MsgQueue<String> key;
    private SocketLink socketLink;
    private Thread tClient;
    private Thread tServer;

    /**
     *
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
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final Duration ANIMATION_TIME = Duration.millis(40);
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
            }
        });
        timeline.getKeyFrames().add(kf);
        timeline.play();
        /*Timer timer = new Timer();
        timer.schedule(new TimerTaskTest(), 1000, 2000);*/
        msgQueue = new MsgQueue<String>();
        key= new MsgQueue<String>();
        socketLink = new SocketLink(msgQueue ,key,5918);
    }
/*
    class TimerTaskTest extends java.util.TimerTask{

        @Override
        public void run() {
            if(!msgQueue.isEmpty()){
                status.setText(msgQueue.recv());
            }
        }
    }*/
}
