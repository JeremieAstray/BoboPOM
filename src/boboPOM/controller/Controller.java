package boboPOM.controller;

import boboPOM.net.MsgQueue;
import boboPOM.view.MainView;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jeremie on 14-3-5.
 */
public class Controller implements Initializable {

    @FXML
    private MainView mainView;

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
    }
/*
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
        key = new MsgQueue<Object>();*/

}
