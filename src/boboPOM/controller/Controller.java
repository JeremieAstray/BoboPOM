package boboPOM.controller;

import boboPOM.config.Config;
import boboPOM.net.MsgQueue;
import boboPOM.view.BackgroundView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jeremie on 14-3-5.
 */
public class Controller implements Initializable {

    @FXML
    private ImageView underBackgroundLeft;
    @FXML
    private ImageView underBackgroundRight;
    @FXML
    private BackgroundView background;


    private Timeline timeline;
    private MsgQueue<String> msgQueue;
    private MsgQueue<Object> key;
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
        key = new MsgQueue<Object>();
    }

    private void initializeModule() {
        Image image = new Image(Config.RESOURCES_PATH + "/images/background.png", Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, true, false);
        Image underBackgroundLeftImage = new Image(Config.RESOURCES_PATH + "/images/underBackground.png");
        underBackgroundLeft.setImage(underBackgroundLeftImage);
        underBackgroundRight.setImage(underBackgroundLeftImage);
        underBackgroundLeft.setLayoutX(Config.UNDER_BACKGROUND_LEFT_X);
        underBackgroundLeft.setLayoutY(Config.UNDER_BACKGROUND_Y);
        underBackgroundRight.setLayoutX(Config.UNDER_BACKGROUND_RIGHT_X);
        underBackgroundRight.setLayoutY(Config.UNDER_BACKGROUND_Y);
        background.setBackgroundImage(image);
        background.setVisible(true);
    }
}
