/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boboPOM.view.menu;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 *
 * @author:feng
 */
public class ImageEditor {

    private int leftRetainWidth;
    private int rightRetainWidth;
    private int upRetainHeight;
    private int downRetainHeight;

    public ImageEditor(int leftRetainWidth, int rightRetainWidth, int upRetainHeight, int downRetainHeight) {
        this.leftRetainWidth = leftRetainWidth;
        this.rightRetainWidth = rightRetainWidth;
        this.upRetainHeight = upRetainHeight;
        this.downRetainHeight = downRetainHeight;
    }

    public Image ChangeWidth(Image origin, int changeWidth) {
        int minWidth = this.leftRetainWidth + this.rightRetainWidth;
        if (minWidth > changeWidth) {
            changeWidth = minWidth;
        }

        PixelReader pixelReader = origin.getPixelReader();
        WritableImage writableImage = new WritableImage(changeWidth, (int) origin.getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        int originPixelX;
        for (int i = 0; i < writableImage.getWidth(); i++) {
            if (i <= this.leftRetainWidth) {
                originPixelX = i;
            } else if (i > this.leftRetainWidth && i <= writableImage.getWidth()
                    - this.rightRetainWidth) {
                if (i < origin.getWidth() - this.rightRetainWidth) {
                    originPixelX = i;
                } else {
                    originPixelX = (int) origin.getWidth() - this.rightRetainWidth;
                }
            } else {
                originPixelX = (int) origin.getWidth() - (int) writableImage.getWidth() + i;
            }
            for (int j = 0; j < writableImage.getHeight(); j++) {
                pixelWriter.setArgb(i, j, pixelReader.getArgb(originPixelX, j));
            }
        }
        return writableImage;
    }

    public Image ChangeHeight(Image origin, int changeHeight) {
        int minHeight = this.upRetainHeight + this.downRetainHeight;
        if (minHeight > changeHeight) {
            changeHeight = minHeight;
        }

        PixelReader pixelReader = origin.getPixelReader();
        WritableImage writableImage = new WritableImage((int) origin.getWidth(), changeHeight);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        int originPixelY;
        for (int i = 0; i < writableImage.getHeight(); i++) {
            if (i <= this.upRetainHeight) {
                originPixelY = i;
            } else if (i > this.upRetainHeight && i <= writableImage.getHeight()
                    - this.downRetainHeight) {
                if (i < origin.getHeight() - this.downRetainHeight) {
                    originPixelY = i;
                } else {
                    originPixelY = (int) origin.getHeight() - this.downRetainHeight;
                }
            } else {
                originPixelY = (int) origin.getHeight() - (int) writableImage.getHeight() + i;
            }
            for (int j = 0; j < writableImage.getWidth(); j++) {
                pixelWriter.setArgb(j, i, pixelReader.getArgb(j, originPixelY));
            }
        }
        return writableImage;
    }

    public Image AddWidth(Image origin, int addWidth) {
        int changeWidth = (int) origin.getWidth() + addWidth;
        return ChangeWidth(origin, changeWidth);
    }

    public Image SubWidth(Image origin, int subWidth) {
        int changeWidth = (int) origin.getWidth() - subWidth;
        return ChangeWidth(origin, changeWidth);
    }

    public Image AddHeight(Image origin, int addHeight) {
        int changeHeight = (int) origin.getHeight() + addHeight;
        return ChangeHeight(origin, changeHeight);
    }

    public Image SubHeight(Image origin, int subHeight) {
        int changeHeight = (int) origin.getHeight() - subHeight;
        return ChangeHeight(origin, changeHeight);
    }

    public static Image CreatSymImage(Image origin) {
        PixelReader pixelReader = origin.getPixelReader();
        WritableImage wi = new WritableImage((int) origin.getWidth(),
                (int) origin.getHeight());
        PixelWriter pixelWriter = wi.getPixelWriter();
        for (int i = 0; i < wi.getWidth(); i++) {
            for (int j = 0; j < wi.getHeight(); j++) {
                pixelWriter.setArgb(i, j,
                        pixelReader.getArgb((int) wi.getWidth() - i - 1, j));
            }
        }
        return wi;
    }

    public static Image ExchangeWidthHeight(Image origin) {
        PixelReader pixelReader = origin.getPixelReader();
        WritableImage wi = new WritableImage((int) origin.getHeight(),
                (int) origin.getWidth());
        PixelWriter pixelWriter = wi.getPixelWriter();
        for (int i = 0; i < wi.getWidth(); i++) {
            for (int j = 0; j < wi.getHeight(); j++) {
                pixelWriter.setArgb(i, j, 
                        pixelReader.getArgb(j, i));
            }
        }
        return wi;
    }

    /**
     * @return the leftRetainWidth
     */
    public int getLeftRetainWidth() {
        return leftRetainWidth;
    }

    /**
     * @param leftRetainWidth the leftRetainWidth to set
     */
    public void setLeftRetainWidth(int leftRetainWidth) {
        this.leftRetainWidth = leftRetainWidth;
    }

    /**
     * @return the rightRetainWidth
     */
    public int getRightRetainWidth() {
        return rightRetainWidth;
    }

    /**
     * @param rightRetainWidth the rightRetainWidth to set
     */
    public void setRightRetainWidth(int rightRetainWidth) {
        this.rightRetainWidth = rightRetainWidth;
    }

    /**
     * @return the upRetainHeight
     */
    public int getUpRetainHeight() {
        return upRetainHeight;
    }

    /**
     * @param upRetainHeight the upRetainHeight to set
     */
    public void setUpRetainHeight(int upRetainHeight) {
        this.upRetainHeight = upRetainHeight;
    }

    /**
     * @return the downRetainHeight
     */
    public int getDownRetainHeight() {
        return downRetainHeight;
    }

    /**
     * @param downRetainHeight the downRetainHeight to set
     */
    public void setDownRetainHeight(int downRetainHeight) {
        this.downRetainHeight = downRetainHeight;
    }

}
