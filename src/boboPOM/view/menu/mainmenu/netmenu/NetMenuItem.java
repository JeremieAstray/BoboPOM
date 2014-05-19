/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boboPOM.view.menu.mainmenu.netmenu;

import boboPOM.config.Config;
import boboPOM.view.menu.ImageEditor;
import boboPOM.view.menu.MenuItem;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * @author:feng
 */
public class NetMenuItem extends MenuItem {

    final private Text[] StatusText = {
            new Text("可连接"), new Text("已连接")
    };
    private HBox hBox;
    private Text IPText;
    private boolean connected;
    private StackPane statusStackPane;


    public NetMenuItem(int width, int height, String IP, boolean connected) {
        super(width, height);
        this.connected = connected;
        IPText = new Text(IP);
        init();
    }

    public NetMenuItem(String IP, boolean connected) {
        this(300, 40, IP, connected);
    }

    public NetMenuItem() {
        this(300, 40, "202.133.133", true);
    }

    private void init() {
        this.rectangle.setWidth(this.getIWidth() - 80);

        Font font = new Font(this.getIHeigth() - 15);
        StatusText[0].setFill(Color.GREEN);
        StatusText[0].setFont(font);
        StatusText[1].setFill(Color.RED);
        StatusText[1].setFont(font);
        IPText.setFont(font);

        int index;
        if (connected) {
            index = 1;
        } else {
            index = 0;
        }

        statusStackPane = new StackPane(StatusText[index]);
        statusStackPane.setAlignment(StatusText[index], Pos.CENTER_RIGHT);
        statusStackPane.setMinSize(80, this.getIHeigth());
        ImageEditor imageEditor = new ImageEditor(0, 0, 0, 0);
        Image line = Config.getMemuImages().get(1);
        line = ImageEditor.ExchangeWidthHeight(line);
        line = imageEditor.ChangeHeight(line, 40);
        ImageView lineView = new ImageView(line);

        stackPane.getChildren().remove(1);
        stackPane.setAlignment(IPText, Pos.CENTER_LEFT);
        stackPane.setAlignment(rectangle, Pos.CENTER_LEFT);
        stackPane.setMinWidth(this.getIWidth());
        stackPane.getChildren().addAll(IPText);
        stackPane.setMinSize(this.getIWidth() - 90, this.getIHeigth());

        GridPane gridPane = new GridPane();
        gridPane.setHgap(3);
        gridPane.add(stackPane, 0, 0);
        gridPane.add(lineView, 1, 0);
        gridPane.add(statusStackPane, 2, 0);

        //this.getChildren().remove(0);
        //this.setBackground(true);

        this.getChildren().add(gridPane);
    }

    public String getIPText() {
        return IPText.getText();
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
        statusStackPane.getChildren().remove(0);
        statusStackPane.getChildren().add(StatusText[this.connected ? 0 : 1]);
    }
}
