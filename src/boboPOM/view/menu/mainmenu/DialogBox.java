/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boboPOM.view.menu.mainmenu;

import boboPOM.config.Config;
import boboPOM.view.menu.ImageEditor;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
public class DialogBox extends Control {

    private int Width;
    private int Height;

    private StringBuilder tipContent;
    private String content;
    private int textIndex;
    private int numOfChar = 109;
    private boolean over = false;

    private Text text;
    private TextFlow textFlow;
    private ImageView borderView;
    private ImageEditor borderEditor;
    private ImageView subscriptView;

    private Timeline tTimeline;

    public DialogBox() {
        this(Config.SCREEN_WIDTH - 50, 140);
    }

    public DialogBox(int Width, int Height) {
        this.Width = Width;
        this.Height = Height;
        init();
    }

    private void init() {
        textIndex = 4;
        tipContent = new StringBuilder();
        getContent();
        text = new Text(tipContent.toString());
        text.setFill(Color.WHITE);
        text.setFont(new Font("宋体",25));
        textFlow = new TextFlow(text);
        textFlow.setMaxWidth(this.Width - 20);
        textFlow.setLineSpacing(10);

        Image border = Config.getMemuImages().get(3);
        Image subscript = Config.getMemuImages().get(2);
        borderView = new ImageView(border);
        subscriptView = new ImageView(subscript);

        borderEditor = new ImageEditor(20, 20, 20, 20);

        Timeline sTimeline = new Timeline();
        sTimeline.setCycleCount(Timeline.INDEFINITE);
        sTimeline.setAutoReverse(true);
        sTimeline.getKeyFrames().addAll(new KeyFrame(Duration.ZERO,
                new KeyValue(subscriptView.translateYProperty(), 0)),
                new KeyFrame(new Duration(300),
                        new KeyValue(subscriptView.translateYProperty(), 10))
        );
        sTimeline.play();
        setSubscript(false);

        tTimeline = new Timeline();

        tTimeline.setCycleCount(Timeline.INDEFINITE);
        tTimeline.getKeyFrames().add(new KeyFrame(new Duration(20),
                new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent t) {
                        if (textIndex < content.length() && textIndex < numOfChar) {
                            tipContent.append(content.charAt(textIndex));
                            text.setText(tipContent.toString());
                            textIndex++;
                        } else {
                            gettTimeline().stop();
                            setSubscript(true);
                            if (textIndex >= content.length()) {
                                setOver(true);
                            }
                        }
                    }
                })
        );

        upDateUI();

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(borderView, subscriptView, textFlow);
        AnchorPane.setTopAnchor(subscriptView, Double.valueOf(this.Height - 10));
        AnchorPane.setLeftAnchor(subscriptView, Double.valueOf(this.Width / 2
                - subscript.getWidth() / 2));
        AnchorPane.setTopAnchor(textFlow, Double.valueOf(10));
        AnchorPane.setLeftAnchor(textFlow, Double.valueOf(10));
        this.getChildren().add(anchorPane);
    }

    private void upDateUI() {
        Image border = borderView.getImage();
        border = borderEditor.ChangeWidth(border, Width);
        border = borderEditor.ChangeHeight(border, Height);
        borderView.setImage(border);
    }

    private void getContent() {
        content = null;
        try {
            Scanner input = new Scanner(Config.getStateFile(), "utf-8");
            while (input.hasNext()) {
                content += input.nextLine();
            }
            input.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void setSubscript(boolean appear) {
        this.subscriptView.setVisible(appear);
    }

    public void nextContent() {
        if (textIndex < content.length() && textIndex >= numOfChar) {
            tipContent.delete(0, tipContent.length());
            tTimeline.play();
            subscriptView.setVisible(false);
            numOfChar += numOfChar;
        } else if (textIndex >= content.length()) {
            over = true;
        }
    }

    public void clearContent() {
        over = false;
        tipContent.delete(0, tipContent.length());
        text.setText("");
        subscriptView.setVisible(false);
        textIndex = 4;
        numOfChar = 109;
    }

    /**
     * @return the Width
     */
    public int getDWidth() {
        return Width;
    }

    /**
     * @return the Height
     */
    public int getDHeight() {
        return Height;
    }

    /**
     * @return the over
     */
    public boolean isOver() {
        return over;
    }

    /**
     * @return the tTimeline
     */
    public Timeline gettTimeline() {
        return tTimeline;
    }

    /**
     * @param over the over to set
     */
    public void setOver(boolean over) {
        this.over = over;
    }
}
