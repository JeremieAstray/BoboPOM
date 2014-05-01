/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boboPOM.view.menu;

import boboPOM.config.Config;
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
    private Text text;
    private TextFlow textFlow;
    private ImageView borderView;
    private ImageEditor borderEditor;
    private ImageView subscriptView;

    public DialogBox() {
        this(Config.SCREEN_WIDTH - 10, 100);
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
        text = new Text("　");
        text.setFill(Color.WHITE);
        text.setFont(new Font(20));
        textFlow = new TextFlow(text);
        textFlow.setMaxWidth(this.Width - 10);

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
        
        Timeline tTimeline = new Timeline();

        tTimeline.setCycleCount(Timeline.INDEFINITE);
        tTimeline.getKeyFrames().add(new KeyFrame(new Duration(100),
                new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent t) {
                        if (textIndex < content.length()) {
                            tipContent.append(content.charAt(textIndex));
                            text.setText(tipContent.toString());
                            textIndex++;
                        } else {
                            tTimeline.stop();
                            setSubscript(true);
                        }
                    }
                },
                new KeyValue(textFlow.translateXProperty(), 0),
                new KeyValue(textFlow.translateYProperty(), 0)));
        tTimeline.play();
        //timer.start();

        upDateUI();

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(borderView, subscriptView, textFlow);
        AnchorPane.setTopAnchor(subscriptView, Double.valueOf(this.Height - 5));
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
            Scanner input = new Scanner(Config.getStateFile());
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

}
