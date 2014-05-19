/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boboPOM.view.menu.mainmenu.netmenu;

import boboPOM.config.Config;
import boboPOM.view.menu.ImageEditor;
import boboPOM.view.menu.MenuBar;
import boboPOM.view.menu.MenuItem;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * @author:feng
 */
public class ConnectServerMenu extends MenuBar {

    private int CWidth;
    private int CHeight;

    private int scrollUp = 0;

    private ScrollPane scrollPane;
    private TextField textField;
    private Button button;
    private Timeline textFieldTimeline;
    private Text tipText;

    private Timeline listenSocketTimeTimeline;

    public ConnectServerMenu() {
        this(400, 430, 300);
    }

    public ConnectServerMenu(int CWidth, int CHeight, int itemWidth) {
        super(CWidth, CHeight, itemWidth);
        this.CWidth = CWidth;
        this.CHeight = CHeight;
        init();
    }

    private void init() {
        Text IPText = new Text("服务IP");
        Text statusText = new Text("状态");
        Font font = new Font(this.getMenuItemHeigth() - 15);
        IPText.setFont(font);
        statusText.setFont(font);

        this.nowItemSelected = -1;

        StackPane IPStackPane = new StackPane(IPText);
        StackPane statuStackPane = new StackPane(statusText);
        IPStackPane.setAlignment(IPText, Pos.CENTER_LEFT);
        IPStackPane.setMinSize(this.getMenuItemWidth() - 75,
                this.getMenuItemHeigth());
        statuStackPane.setAlignment(statusText, Pos.CENTER_RIGHT);
        statuStackPane.setMinSize(80, this.getMenuItemHeigth());

        Image lineImage = ImageEditor.ExchangeWidthHeight(line);
        ImageEditor imageEditor = new ImageEditor(0, 0, 0, 0);
        lineImage = imageEditor.ChangeHeight(lineImage, 40);
        ImageView VlineImageView = new ImageView(lineImage);

        ImageView lineImageView = new ImageView(this.line);

        GridPane gridPane = new GridPane();
        gridPane.add(IPStackPane, 0, 0);
        gridPane.add(VlineImageView, 1, 0);
        gridPane.add(statuStackPane, 2, 0);
        gridPane.add(lineImageView, 0, 1, 3, 1);

        scrollPane = new ScrollPane(getvBox());
        scrollPane.setBlendMode(BlendMode.valueOf("DARKEN"));
        scrollPane.setStyle("-fx-background-color: transparent;-fx-border-color: transparent;");
        scrollPane.setMinSize(this.getMenuItemWidth() + 30, this.CHeight - 130
                - this.getMenuItemHeigth());
        gridPane.add(scrollPane, 0, 2, 4, 2);

        StackPane IPEnterStackPane = new StackPane();
        textField = new TextField();
        textField.setMinHeight(30);
        textField.setPromptText("输入服务IP");
        textField.setMinSize(this.getMenuItemWidth() - 30, 30);

        button = new Button("连接服务");
        button.setMinHeight(30);
        button.setId("button");

        IPEnterStackPane.getChildren().addAll(textField, button);
        IPEnterStackPane.setAlignment(textField, Pos.CENTER_LEFT);
        IPEnterStackPane.setAlignment(button, Pos.CENTER_RIGHT);

        tipText = new Text("请输入正确的IP地址");
        tipText.setFill(Color.RED);
        tipText.setFont(new Font(12));
        tipText.setVisible(false);

        GridPane mainPane = new GridPane();
        mainPane.setVgap(5);

        mainPane.add(gridPane, 0, 1, 4, 1);
        mainPane.add(IPEnterStackPane, 0, 3, 4, 3);
        mainPane.add(tipText, 0, 6);

        textFieldTimeline = new Timeline();
        textFieldTimeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(Config.ANIMATION_TIME, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (getTextField().isPressed()) {
                    tipText.setVisible(false);
                    textFieldTimeline.stop();
                }
            }
        });
        textFieldTimeline.getKeyFrames().add(kf);

        ListenSocketTime();

        AnchorPane.setTopAnchor(mainPane, Double.valueOf(30));
        AnchorPane.setLeftAnchor(mainPane, Double.valueOf(this.getBWidth() / 2
                - this.getMenuItemWidth() / 2) - 15);

        anchorPane.getChildren().remove(getvBox());
        anchorPane.getChildren().remove(cursorView);
        anchorPane.getChildren().addAll(mainPane);
    }

    //监听已检测到的未连接socket的存在时间，若时间超过一分钟，则将之清除
    private void ListenSocketTime() {
        listenSocketTimeTimeline = new Timeline();
        listenSocketTimeTimeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(new Duration(60000), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                getvBox().getChildren().clear();
                items.clear();
                nowItemSelected = -1;
            }
        });
        listenSocketTimeTimeline.getKeyFrames().add(kf);
    }

    @Override
    public void addItem(MenuItem menuItem) {
        if (isAdd(menuItem)) {
            if (nowItemSelected == -1) {
                nowItemSelected = 0;
            }
            menuItem.setOnMouseEntered(enterMouseEvent(menuItem, items.size()));
            menuItem.setOnMouseExited(exitMouseEvent(menuItem));
            items.add(menuItem);
            ImageView lineView = new ImageView(this.line);

            if (items.size() == 1) {
                AnchorPane ap = new AnchorPane(items.get(0));
                ap.setMinHeight(this.getMenuItemHeigth());
                getvBox().getChildren().add(ap);
            } else {
                AnchorPane ap = new AnchorPane();
                ap.getChildren().addAll(lineView, items.get(items.size() - 1));
                AnchorPane.setTopAnchor(lineView, Double.valueOf(-4));
                ap.setMinHeight(4 + this.getMenuItemHeigth());
                getvBox().getChildren().add(ap);
            }
        }
    }

    private boolean isAdd(MenuItem netMenuItem) {
        NetMenuItem menuItem = null;
        NetMenuItem menuItem2 = (NetMenuItem) netMenuItem;
        for (int i = 0; i < this.items.size(); i++) {
            menuItem = (NetMenuItem) this.items.get(i);
            if (menuItem.getIPText().equals(menuItem2.getIPText())) {
                if (menuItem.isConnected() == menuItem2.isConnected()) {
                    return false;
                } else {
                    ((NetMenuItem) this.items.get(i)).setConnected(true);
                    return false;
                }
            }
        }
        return true;
    }

    public boolean ButtonActionDeal() {
        String text = textField.getText();
        if (text != null && !text.trim().equals("")) {
            if (isCorrectIP(text)) {
                return true;
            } else {
                tipText.setVisible(true);
                textFieldTimeline.play();
            }
        }
        return false;
    }

    private boolean isCorrectIP(String IP) {
        String regx = "((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))";
        return IP.matches(regx);
    }

    public NetMenuItem getSelectedMenuItem() {
        return ((NetMenuItem) this.items.get(nowItemSelected));
    }

    @Override
    public void reset() {
        super.reset();
        this.textFieldTimeline.stop();
        this.getvBox().getChildren().clear();
        this.items.clear();
        this.nowItemSelected = -1;
    }

    @Override
    public void DealKeyEvent(KeyCode t) {
        if (nowItemSelected != -1) {
            super.DealKeyEvent(t);
            int selected = this.getSelectedItem();
            if (selected > scrollUp + 5) {
                if (selected == this.items.size() - 1) {
                    scrollUp = this.items.size() - 6;
                }
                scrollUp++;

            } else if (selected < scrollUp) {
                if (selected == 0) {
                    scrollUp = 0;
                } else {
                    scrollUp--;
                }
            }
            scrollPane.setVvalue(scrollUp * 1.0 / (this.items.size() - 6));
        }
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    /**
     * @return the button
     */
    public Button getButton() {
        return button;
    }

    public String getTextFieldIP() {
        return textField.getText();
    }

    /**
     * @return the listenSocketTimeTimeline
     */
    public Timeline getListenSocketTimeTimeline() {
        return listenSocketTimeTimeline;
    }

    /**
     * @return the textField
     */
    public TextField getTextField() {
        return textField;
    }
}
