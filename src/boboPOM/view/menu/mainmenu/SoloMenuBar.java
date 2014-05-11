/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boboPOM.view.menu.mainmenu;

import boboPOM.config.Config;
import boboPOM.view.menu.ImageEditor;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 *
 * @author:feng
 */
public class SoloMenuBar extends PlayerMenu {

    private ImageView cursorView2P;
    private int itemSelected2P = 0;

    public SoloMenuBar() {
        this(250, 70, 200);
    }

    public SoloMenuBar(int Width, int Height, int itemWidth) {
        super(Width, Height, itemWidth);
        init();
    }

    private void init() {
        Image cursor = Config.getMemuImages().get(0);
        cursorView2P = new ImageView(ImageEditor.CreatSymImage(cursor));

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.getKeyFrames().addAll(new KeyFrame(Duration.ZERO,
                new KeyValue(cursorView2P.translateYProperty(), 0)),
                new KeyFrame(new Duration(300),
                        new KeyValue(cursorView2P.translateYProperty(), 10))
        );
        timeline.play();

        anchorPane.getChildren().add(cursorView2P);
        AnchorPane.setLeftAnchor(cursorView2P, Double.valueOf(this.getBWidth() / 2
                + this.getMenuItemWidth() / 2));
        AnchorPane.setTopAnchor(cursorView2P, Double.valueOf(this.getMenuItemHeigth()));
    }

    public void Deal2PKeyEvent(KeyCode t) {
        KeyCode keyCode = t;
        if (keyCode == keyCode.UP || keyCode == keyCode.DOWN) {
            if (itemSelected2P != -1 && itemSelected2P != this.getSelectedItem()) {
                items.get(itemSelected2P).setBackground(false);
            }
            if (keyCode == KeyCode.DOWN) {
                if (itemSelected2P == -1 || itemSelected2P == items.size() - 1) {
                    itemSelected2P = 0;
                } else {
                    itemSelected2P++;
                }
            } else if (keyCode == KeyCode.UP) {
                if (itemSelected2P == -1 || itemSelected2P == 0) {
                    itemSelected2P = items.size() - 1;
                } else {
                    itemSelected2P--;
                }
            }
            items.get(itemSelected2P).setBackground(true);
            change2PCursorLocation(itemSelected2P);
        }
    }
    
    @Override
    public void DealKeyEvent(KeyCode t){
        super.DealKeyEvent(t);
        if(items.get(itemSelected2P).isSelected() == false){
            items.get(itemSelected2P).setBackground(true);
        }
    }

    private void change2PCursorLocation(int index) {
        AnchorPane.setTopAnchor(cursorView2P,
                Double.valueOf(index * (this.getMenuItemHeigth() + 4)
                        + this.getMenuItemHeigth()));
    }

    @Override
    public void reset() {
        super.reset();
        if (itemSelected2P != 0) {
            items.get(itemSelected2P).setBackground(false);
            itemSelected2P = 0;
            change2PCursorLocation(0);
        }
    }

    /**
     * @return the itemSelected2P
     */
    public int getItemSelected2P() {
        return itemSelected2P;
    }
}
