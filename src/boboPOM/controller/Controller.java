package boboPOM.controller;

import boboPOM.util.SocketLink;
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
    private Thread tClient;
    private Thread tServer;


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

        //定义时间轴
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(Config.ANIMATION_TIME, new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            }
        });
        timeline.getKeyFrames().add(kf);
        timeline.play();
        //定义消息队列
        msgQueue = new MsgQueue<String>();
        key = new MsgQueue<String>();
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
