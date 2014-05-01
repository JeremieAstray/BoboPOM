/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boboPOM.view.menu;

import boboPOM.config.Config;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 *
 * @author:feng
 */
public class BaseMenuBar extends Control {

    private int BWidth;
    private int BHeight;
    private int nowItemSelected = -1;
    final private int menuItemHeigth = 50;
    final private int menuItemWidth = 200;

    private ImageView cursorView;
    private ImageView borderView;
    private Image line;
    private ImageEditor borderEditor;
    private ImageEditor lineEditor;
    private ArrayList<MenuItem> items;
    private AnchorPane anchorPane;
    private VBox vBox;
    private VBox lineVBox;

    public BaseMenuBar() {
        this(100, 20);
    }

    public BaseMenuBar(int width, int height) {
        this.BWidth = width;
        this.BHeight = height;
        init();
    }

    private void init() {
        items = new ArrayList<MenuItem>();
        anchorPane = new AnchorPane();
        vBox = new VBox(5);
        lineVBox = new VBox(this.menuItemHeigth + 1);

        Image baseBorder = Config.getMemuImages().get(4);
        Image cursor = Config.getMemuImages().get(0);
        line = Config.getMemuImages().get(1);

        borderView = new ImageView(baseBorder);
        cursorView = new ImageView(cursor);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.getKeyFrames().addAll(new KeyFrame(Duration.ZERO,
                new KeyValue(cursorView.translateYProperty(), 0)),
                new KeyFrame(new Duration(300),
                        new KeyValue(cursorView.translateYProperty(), 10))
        );
        timeline.play();

        borderEditor = new ImageEditor(20, 30, 30, 30);
        lineEditor = new ImageEditor(10, 20, 0, 0);
        line = lineEditor.ChangeWidth(line, this.menuItemWidth - 10);

        getAnchorPane().getChildren().addAll(borderView, cursorView, lineVBox, vBox);
        updatUI();

        this.getChildren().add(getAnchorPane());
    }

    private void updatUI() {
        Image baseBorder = borderView.getImage();
        baseBorder = borderEditor.ChangeWidth(baseBorder, getBWidth());
        baseBorder = borderEditor.ChangeHeight(baseBorder, getBHeight());
        borderView.setImage(baseBorder);

        AnchorPane.setLeftAnchor(cursorView, Double.valueOf(this.BWidth / 2
                - this.menuItemWidth / 2 - 40));
        AnchorPane.setTopAnchor(cursorView, Double.valueOf(0));
        AnchorPane.setLeftAnchor(vBox, Double.valueOf(this.BWidth / 2
                - this.menuItemWidth / 2));
        AnchorPane.setTopAnchor(lineVBox, Double.valueOf(this.menuItemHeigth + 35));
        AnchorPane.setLeftAnchor(lineVBox, Double.valueOf(this.BWidth / 2
                - line.getWidth() / 2));
    }

    public void addItem(MenuItem menuItem) {
        menuItem.setOnMouseEntered(enterMouseEvent(menuItem, items.size()));
        menuItem.setOnMouseExited(exitMouseEvent(menuItem, items.size() - 1));
        //menuItem.setOnMousePressed(pressMouseEvent());
        items.add(menuItem);
        ImageView lineView = new ImageView(this.line);

        Image baseBorder = borderView.getImage();
        baseBorder = borderEditor.AddHeight(baseBorder, this.menuItemHeigth + 1);
        borderView.setImage(baseBorder);
        if (items.size() == 1) {
            vBox.getChildren().add(items.get(0));
        } else {
            vBox.getChildren().add(items.get(items.size() - 1));
            lineVBox.getChildren().add(lineView);
        }

    }

    private EventHandler<MouseEvent> enterMouseEvent(MenuItem menuItem, int index) {
        return new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                if(nowItemSelected != -1 && nowItemSelected != index){
                    items.get(nowItemSelected).setBackground(false);
                }
                menuItem.setBackground(true);
                changeCursorLocation(index);
            }
        };
    }

    private EventHandler<MouseEvent> exitMouseEvent(MenuItem menuItem, int index) {
        return new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                menuItem.setBackground(false);
            }
        };
    }

    private void changeCursorLocation(int index) {
        AnchorPane.setTopAnchor(cursorView,
                Double.valueOf(index * (menuItemHeigth + 5) + 10));
    }

    public void DealKeyEvent(KeyCode t) {
        KeyCode keyCode = t;
        if (keyCode == keyCode.W || keyCode == keyCode.S) {
            if (nowItemSelected != -1) {
                items.get(nowItemSelected).setBackground(false);
            }
            if (keyCode == KeyCode.S) {
                if (nowItemSelected == -1 || nowItemSelected == items.size() - 1) {
                    nowItemSelected = 0;
                } else {
                    nowItemSelected++;
                }
            } else if (keyCode == KeyCode.W) {
                if (nowItemSelected == -1 || nowItemSelected == 0) {
                    nowItemSelected = items.size() - 1;
                } else {
                    nowItemSelected--;
                }
            }
            items.get(nowItemSelected).setBackground(true);
            changeCursorLocation(nowItemSelected);
        }
    }

    public void setSize(int width, int height) {
        this.BWidth = width;
        this.BHeight = height;
        updatUI();
    }

    /**
     * @return the BWidth
     */
    public int getBWidth() {
        return BWidth;
    }

    /**
     * @param BWidth the BWidth to set
     */
    public void setBWidth(int BWidth) {
        this.BWidth = BWidth;
    }

    /**
     * @return the BHeight
     */
    public int getBHeight() {
        return BHeight;
    }

    /**
     * @param BHeight the BHeight to set
     */
    public void setBHeight(int BHeight) {
        this.BHeight = BHeight;
        borderView.setImage(borderEditor.ChangeHeight(borderView.getImage(), getBHeight()));
    }

    /**
     * @return the anchorPane
     */
    public AnchorPane getAnchorPane() {
        return anchorPane;
    }
}
