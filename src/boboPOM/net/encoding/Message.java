/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boboPOM.net.encoding;

import javafx.scene.Node;

/**
 * @author Administrator
 */
public abstract class Message implements java.io.Serializable {// network use
    private double translataX, translateY;
    private double opacity, scaleX, scaleY;
    private boolean visible;

    protected Message(Node no) {
        this.opacity = no.getOpacity();
        this.scaleX = no.getScaleX();
        this.scaleY = no.getScaleY();
        this.translataX = no.getTranslateX();
        this.translateY = no.getTranslateY();
        this.visible = no.isVisible();
    }

    public double getTranslataX() {
        return translataX;
    }

    public double getTranslateY() {
        return translateY;
    }

    public double getOpacity() {
        return opacity;
    }

    public double getScaleX() {
        return scaleX;
    }

    public double getScaleY() {
        return scaleY;
    }

    public boolean isVisible() {
        return visible;
    }


}
