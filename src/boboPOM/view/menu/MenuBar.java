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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 *
 * @author:feng
 */
public class MenuBar extends Control {

    private int BWidth;
    private int BHeight;
    protected int nowItemSelected = 0;
    final private int menuItemHeigth = 40;
    private int menuItemWidth = 200;

    protected ImageView cursorView;
    private ImageView borderView;
    protected Image line;
    private ImageEditor borderEditor;
    private ImageEditor lineEditor;
    protected VBox vBox;

    protected KeyFrame keyFrameStar;
    protected KeyFrame keyFrameEnd;
    protected Timeline timeline;
    protected ArrayList<MenuItem> items;
    protected AnchorPane anchorPane;

    public MenuBar() {
        this(250, 70, 200);
    }

    public MenuBar(int width, int height, int itemWidth) {
        this.BWidth = width;
        this.BHeight = height;
        this.menuItemWidth = itemWidth;
        init();
    }

    private void init() {
        items = new ArrayList<MenuItem>();
        anchorPane = new AnchorPane();
        vBox = new VBox(0);

        Image baseBorder = Config.getMemuImages().get(4);
        Image cursor = Config.getMemuImages().get(0);
        line = Config.getMemuImages().get(1);

        borderView = new ImageView(baseBorder);
        cursorView = new ImageView(cursor);
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        keyFrameStar = new KeyFrame(Duration.ZERO,
                new KeyValue(cursorView.translateYProperty(), 0));
        keyFrameEnd = new KeyFrame(new Duration(300),
                new KeyValue(cursorView.translateYProperty(), 10));
        timeline.getKeyFrames().addAll(keyFrameStar, keyFrameEnd);
        timeline.play();

        borderEditor = new ImageEditor(20, 30, 30, 30);
        lineEditor = new ImageEditor(10, 20, 0, 0);
        line = lineEditor.ChangeWidth(line, this.menuItemWidth - 10);

        AnchorPane.setTopAnchor(vBox, Double.valueOf(30));
        getAnchorPane().getChildren().addAll(borderView, cursorView, vBox);
        updatUI();

        this.getChildren().add(getAnchorPane());
    }

    private void updatUI() {
        Image baseBorder = borderView.getImage();
        baseBorder = borderEditor.ChangeWidth(baseBorder, getBWidth());
        baseBorder = borderEditor.ChangeHeight(baseBorder, getBHeight());
        BWidth = (int) baseBorder.getWidth();
        BHeight = (int) baseBorder.getHeight();
        borderView.setImage(baseBorder);

        AnchorPane.setLeftAnchor(cursorView, Double.valueOf(this.BWidth / 2
                - this.menuItemWidth / 2 - 35));
        AnchorPane.setTopAnchor(cursorView, Double.valueOf(-5));
        AnchorPane.setLeftAnchor(vBox, Double.valueOf(this.BWidth / 2
                - this.menuItemWidth / 2));
    }

    public void addItem(MenuItem menuItem) {
        menuItem.setOnMouseEntered(enterMouseEvent(menuItem, items.size()));
        menuItem.setOnMouseExited(exitMouseEvent(menuItem, items.size() - 1));
        items.add(menuItem);
        ImageView lineView = new ImageView(this.line);

        addBHeight(this.menuItemHeigth);
        if (items.size() == 1) {
            items.get(0).setBackground(true);
            AnchorPane ap = new AnchorPane(items.get(0));
            ap.setMinHeight(this.getMenuItemHeigth());
            vBox.getChildren().add(ap);
        } else {
            AnchorPane ap = new AnchorPane();
            ap.getChildren().addAll(lineView, items.get(items.size() - 1));
            AnchorPane.setTopAnchor(lineView, Double.valueOf(-4));
            ap.setMinHeight(4 + this.getMenuItemHeigth());
            vBox.getChildren().add(ap);
        }

    }

    protected void addBHeight(int height) {
        Image baseBorder = borderView.getImage();
        baseBorder = borderEditor.AddHeight(baseBorder, height);
        BHeight = (int) baseBorder.getHeight();
        borderView.setImage(baseBorder);
    }

    protected EventHandler<MouseEvent> enterMouseEvent(MenuItem menuItem, int index) {
        return new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                if (nowItemSelected != -1 && nowItemSelected != index) {
                    items.get(nowItemSelected).setBackground(false);
                }
                menuItem.setBackground(true);
                nowItemSelected = index;
                changeCursorLocation(index);
                Config.effectMedia.play(3);
            }
        };
    }

    protected EventHandler<MouseEvent> exitMouseEvent(MenuItem menuItem, int index) {
        return new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                if (vBox.isHover()) {
                    menuItem.setBackground(false);
                }
            }
        };
    }

    protected void changeCursorLocation(int index) {
        AnchorPane.setTopAnchor(cursorView,
                Double.valueOf(index * (menuItemHeigth + 4)));
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
            changeCursorLocation(nowItemSelected);
            items.get(nowItemSelected).setBackground(true);
            Config.effectMedia.play(3);
        }
    }

    public void reset() {
        if (nowItemSelected != -1) {
            items.get(nowItemSelected).setBackground(false);
            nowItemSelected = 0;
            changeCursorLocation(0);
        }
    }

    public int getSelectedItem() {
        return nowItemSelected;
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

    /**
     * @return the menuItemHeigth
     */
    public int getMenuItemHeigth() {
        return menuItemHeigth;
    }

    /**
     * @return the menuItemWidth
     */
    public int getMenuItemWidth() {
        return menuItemWidth;
    }
}
