/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boboPOM.view.menu;

import boboPOM.config.Config;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author:feng
 */
public class MenuItem extends Parent {

    private boolean selected;
    private int IHeigth;
    private int IWidth;
    private Rectangle rectangle;
    private Text text;
    private LinearGradient linearGradient;
    private ImageView cursorView;
    private StackPane stackPane;

    public MenuItem(String name, int width, int height) {
        this.text = new Text(name);
        this.IWidth = width;
        this.IHeigth = height;
        init();

    }

    private void init() {
        Image cursor = Config.getMemuImages().get(0);
        cursorView = new ImageView(cursor);
        

        Stop[] stops = new Stop[]{new Stop(0, Color.web("#00BFFF", 0.1)),
            new Stop(0.3, Color.web("#00BFFF", 0.5)),
            new Stop(0.5, Color.web("#00BFFF", 1)),
            new Stop(0.7, Color.web("#00BFFF", 0.5)),
            new Stop(1, Color.web("#00BFFF", 0.1))
        };

        linearGradient = new LinearGradient(0, 0, 1, 0, true,
                CycleMethod.NO_CYCLE, stops);

        this.rectangle = new Rectangle(this.getIWidth(), this.getIHeigth());

        Font font = new Font(this.getIHeigth() - 15);
        text.setFont(font);

        stackPane = new StackPane();
        getStackPane().setAlignment(this.text, Pos.CENTER);
        getStackPane().getChildren().addAll(rectangle, text);

        this.setBackground(false);
        this.getChildren().add(stackPane);
    }

    public void setBackground(boolean selected) {
        this.setSelected(selected);
        if (selected) {
            this.rectangle.setFill(linearGradient);
        } else {
            this.rectangle.setFill(Color.web("#00BFFF", 0));
        }
    }


    /**
     * @return the rectangle
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * @param IHeigth the IHeigth to set
     */
    public void setIHeigth(int IHeigth) {
        this.IHeigth = IHeigth;
    }

    /**
     * @param IWidth the IWidth to set
     */
    public void setIWidth(int IWidth) {
        this.IWidth = IWidth;
    }

    /**
     * @return the IHeigth
     */
    public int getIHeigth() {
        return IHeigth;
    }

    /**
     * @return the IWidth
     */
    public int getIWidth() {
        return IWidth;
    }

    /**
     * @return the stackPane
     */
    public StackPane getStackPane() {
        return stackPane;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
