/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boboPOM.view.menu.mainmenu.netmenu;

import boboPOM.config.Config;
import boboPOM.view.menu.ImageEditor;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

/**
 *
 * @author:feng
 */
public class SocketMenu extends Control {

    private int SWidth;
    private int SHeight;

    private ImageView borderView;

    protected String waitString;
    private StringBuilder wait;
    final private int spotMaxTime = 3;
    private int spotTime = 0;
    protected Text waitText;
    protected Timeline timeline;

    private Text connectText;
    private TextFlow textFlow;

    private boolean connected;
    
    protected AnchorPane anchorPane;

    public SocketMenu() {
        this("等待连接中", 250, 100);
    }   

    public SocketMenu(String waitTip, int width, int height) {
        this.waitString = waitTip;
        this.SWidth = width;
        this.SHeight = height;

        connected = false;
        init();
        setStatus();

        anchorPane = new AnchorPane(borderView, textFlow, waitText);

        AnchorPane.setTopAnchor(textFlow, Double.valueOf(45));
        AnchorPane.setLeftAnchor(textFlow, Double.valueOf(this.SWidth / 2
                - 6 * (connectText.getText().length() + 8)));

        AnchorPane.setTopAnchor(waitText, Double.valueOf(40));
        AnchorPane.setLeftAnchor(waitText, Double.valueOf(this.SWidth / 2
                - 14 * waitString.length()));

        this.getChildren().add(anchorPane);
    }

    private void init() {
        Image border = Config.getMemuImages().get(4);
        ImageEditor imageEditor = new ImageEditor(20, 30, 30, 30);
        border = imageEditor.ChangeWidth(border, SWidth);
        border = imageEditor.ChangeHeight(border, SHeight);
        borderView = new ImageView(border);
//        this.SWidth = (int) border.getWidth();
//        this.SHeight = (int) border.getHeight();

        wait = new StringBuilder(waitString);
        waitText = new Text(wait.toString());
        waitText.setFont(new Font(25));
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(new Duration(250),
                new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent t) {
                        if (!isConnected()) {
                            if (spotTime < spotMaxTime) {
                                wait.append('.');
                                spotTime++;
                            } else {
                                spotTime = 0;
                                wait.delete(waitString.length(),
                                        waitString.length() + spotMaxTime);
                            }
                            waitText.setText(wait.toString());
                        } else {
                            timeline.stop();
                        }
                    }
                })
        );
        timeline.play();

        Font font = new Font(18);
        connectText = new Text();
        connectText.setFill(Color.RED);
        connectText.setFont(font);
        Text[] texts = {new Text("IP: "), new Text(" 连入成功")};
        texts[0].setFont(font);
        texts[1].setFont(font);
        textFlow = new TextFlow(texts[0], connectText, texts[1]);
    }

    private void setStatus() {
        textFlow.setVisible(connected);
        waitText.setVisible(!connected);
    }

    public void setConnected(String IP) {
        this.connected = true;
        connectText.setText(IP);
        AnchorPane.setLeftAnchor(textFlow, Double.valueOf(this.borderView.
                getImage().getWidth() / 2 - 6 * (connectText.getText().length() + 8)));
        setStatus();
    }

    /**
     * @return the SWidth
     */
    public int getSWidth() {
        return SWidth;
    }

    /**
     * @return the SHeight
     */
    public int getSHeight() {
        return SHeight;
    }

    /**
     * @return the connected
     */
    public boolean isConnected() {
        return connected;
    }
}
