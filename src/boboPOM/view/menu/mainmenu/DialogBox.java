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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author:feng
 */
public class DialogBox extends Control {

    private int Width;
    private int Height;

    private StringBuffer lineBuffer;
    private int textIndex;
    private int numOfChar = 109;
    private boolean over = false;

    private int numOfLineInBox;
    private int[] linesNum;
    private int lineIndex;
    private int currentNumOfLine;
    private int lineCharIndex;
    private boolean isGPAdd;
    private ArrayList<String> lines;
    private Text[] texts;
    private GridPane textGridPane;

    private ImageView borderView;
    private ImageEditor borderEditor;
    private ImageView subscriptView;

    private Timeline tTimeline;

    public DialogBox() {
        this(Config.SCREEN_WIDTH - 50, 140,
                new int[]{4, 3, 4, 3, 4, 2});
    }

    public DialogBox(int Width, int Height, int[] linesNum) {
        this.Width = Width;
        this.Height = Height;
        this.linesNum = linesNum;
        init();
    }

    private void init() {
        textIndex = 0;
        currentNumOfLine = 0;
        lineCharIndex = 0;
        isGPAdd = false;
        lineBuffer = new StringBuffer();
        lines = new ArrayList<String>();
        lineIndex = 0;
        numOfLineInBox = linesNum[lineIndex];

        getContent();
        texts = new Text[numOfLineInBox];

        textGridPane = new GridPane();

        Font font = new Font("宋体", 25);
        for (int i = 0; i < texts.length; i++) {
            texts[i] = new Text();
            texts[i].setFill(Color.WHITE);
            texts[i].setFont(font);
        }

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
        tTimeline.getKeyFrames().add(new KeyFrame(new Duration(30),
                        new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent t) {
                                if (textIndex < lines.size()
                                        && currentNumOfLine < numOfLineInBox) {
                                    if (!isGPAdd) {
                                        textGridPane.add(texts[currentNumOfLine],
                                                0, currentNumOfLine);
                                        isGPAdd = true;
                                    }
                                    if (lineCharIndex < lines.get(textIndex).length()) {
                                        for (int i = 0; i < 2 && lineCharIndex < lines.get(textIndex).length(); i++) {
                                            lineBuffer.append(lines.get(textIndex).charAt(lineCharIndex));
                                            lineCharIndex++;
                                        }
                                        if (!Config.effectMedia.isPlaying()) {
                                            Config.effectMedia.play(4);
                                        }
                                        texts[currentNumOfLine].setText(lineBuffer.toString());
                                    } else {
                                        lineBuffer.delete(0, lineCharIndex);
                                        isGPAdd = false;
                                        currentNumOfLine++;
                                        textIndex++;
                                        lineCharIndex = 0;
                                    }
                                } else {
                                    tTimeline.stop();
                                    setSubscript(true);
                                    if (textIndex >= lines.size()) {
                                        setOver(true);
                                    }
                                }
                            }
                        }
                )
        );

        upDateUI();

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(borderView, subscriptView, textGridPane);
        AnchorPane.setTopAnchor(subscriptView, Double.valueOf(this.Height - 10));
        AnchorPane.setLeftAnchor(subscriptView, Double.valueOf(this.Width / 2
                - subscript.getWidth() / 2));
        AnchorPane.setTopAnchor(textGridPane, Double.valueOf(10));
        AnchorPane.setLeftAnchor(textGridPane, Double.valueOf(10));
        this.getChildren().add(anchorPane);
    }

    private void upDateUI() {
        Image border = borderView.getImage();
        border = borderEditor.ChangeWidth(border, Width);
        border = borderEditor.ChangeHeight(border, Height);
        borderView.setImage(border);
    }

    private void getContent() {
//        try {
//            Scanner input = new Scanner(Config.getStateFile(), "utf-8");
//            while (input.hasNext()) {
//                lines.add(input.nextLine());
//            }
//            input.close();
//        } catch (FileNotFoundException ex) {
//            ex.printStackTrace();
//        }
        BufferedReader bufferedReader = new BufferedReader(Config.inputStreamReader);
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException ex) {
            System.err.println("Dialog IOException");
            ex.printStackTrace();
        }
    }

    public void setSubscript(boolean appear) {
        this.subscriptView.setVisible(appear);
    }

    public void nextContent() {
        if (textIndex < lines.size() && currentNumOfLine >= numOfLineInBox) {
            numOfLineInBox = linesNum[++lineIndex];
            for (int i = 0; i < texts.length; i++) {
                texts[i].setText("");
            }
            subscriptView.setVisible(false);
            textGridPane.getChildren().clear();
            currentNumOfLine = 0;
            tTimeline.play();
        } else if (textIndex >= lines.size()) {
            over = true;
        }
    }

    public void clearContent() {
        over = false;
        for (int i = 0; i < texts.length; i++) {
            texts[i].setText("");
        }
        textGridPane.getChildren().clear();
        subscriptView.setVisible(false);
        textIndex = 0;
        lineIndex = 0;
        numOfLineInBox = linesNum[lineIndex];
        currentNumOfLine = 0;
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
     * @param over the over to set
     */
    public void setOver(boolean over) {
        this.over = over;
    }

    /**
     * @return the tTimeline
     */
    public Timeline gettTimeline() {
        return tTimeline;
    }
}
