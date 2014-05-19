/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boboPOM.view.menu.mainmenu;

import boboPOM.config.Config;
import boboPOM.view.menu.ImageEditor;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * @author:feng
 */
public class SoloPrepareBar extends Parent {

    private int SWidth;
    private int SHeight;

    private boolean prepared1P = false;
    private boolean prepared2P = false;

    private StackPane sp1P;
    private StackPane sp2P;

    private ImageView borderView1P;
    private ImageView borderView2P;

    private String string1P;
    private String string2P;

    public SoloPrepareBar() {
        this("1P已选择", "2P已选择", 150, 45);
    }

    public SoloPrepareBar(String string1P, String string2P, int width, int height) {
        this.string1P = string1P;
        this.string2P = string2P;
        this.SWidth = width;
        this.SHeight = height;
        init();
    }

    private void init() {
        Image border = Config.getMemuImages().get(3);
        ImageEditor imageEditor = new ImageEditor(20, 30, 20, 20);
        border = imageEditor.ChangeWidth(border, SWidth);
        border = imageEditor.ChangeHeight(border, SHeight);
        borderView1P = new ImageView(border);
        borderView2P = new ImageView(border);

        Text text1P = new Text(string1P);
        Text text2P = new Text(string2P);
        Font font = new Font(25);
        text1P.setFont(font);
        text2P.setFont(font);
        text1P.setFill(Color.WHITE);
        text2P.setFill(Color.WHITE);

        sp1P = new StackPane(borderView1P, text1P);
        sp1P.setAlignment(text1P, Pos.CENTER);
        sp2P = new StackPane(borderView2P, text2P);
        sp2P.setAlignment(text2P, Pos.CENTER);

        sp1P.setVisible(false);
        sp2P.setVisible(false);

        sp1P.setTranslateX(Config.getSCREEN_WIDTH() / 2 - 300);
        sp1P.setTranslateY(Config.getSCREEN_HEIGHT() / 2);
        sp2P.setTranslateX(Config.getSCREEN_WIDTH() / 2 + 150);
        sp2P.setTranslateY(Config.getSCREEN_HEIGHT() / 2);

        this.getChildren().addAll(sp1P, sp2P);
    }

    public void set1PPrepared(boolean isPrepared) {
        this.prepared1P = isPrepared;
        System.out.println(isPrepared);
        this.sp1P.setVisible(isPrepared);

    }

    public void set2PPrepared(boolean isPrepared) {
        this.prepared2P = isPrepared;
        this.sp2P.setVisible(isPrepared);
    }

    public int isPrepared() {
        if (prepared1P && prepared2P) {
            return 2;
        } else if (prepared2P) {
            return 1;
        } else if (prepared1P) {
            return 0;
        }
        return -1;
    }

    public void reset() {
        this.sp1P.setVisible(false);
        this.sp2P.setVisible(false);
        this.prepared1P = false;
        this.prepared2P = false;
    }
}
