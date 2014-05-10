/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boboPOM.view.menu.mainmenu.netmenu;

import boboPOM.view.menu.ImageEditor;
import boboPOM.view.menu.MenuBar;
import boboPOM.view.menu.MenuItem;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * @author:feng
 */
public class ConnectServerMenu extends MenuBar {

    private int CWidth;
    private int CHeight;

    public ConnectServerMenu() {
        this(400, 410, 300);
    }

    public ConnectServerMenu(int CWidth, int CHeight, int itemWidth) {
        super(CWidth, CHeight, itemWidth);
        this.CWidth = CWidth;
        this.CHeight = CHeight;
        init();
    }

    private void init() {
        Text IPText = new Text("服务IP");
        Text statusText = new Text("状态");
        Font font = new Font(this.getMenuItemHeigth() - 15);
        IPText.setFont(font);
        statusText.setFont(font);

        StackPane IPStackPane = new StackPane(IPText);
        StackPane statuStackPane = new StackPane(statusText);
        IPStackPane.setAlignment(IPText, Pos.CENTER_LEFT);
        IPStackPane.setMinSize(this.getMenuItemWidth() - 90,
                this.getMenuItemHeigth());
        statuStackPane.setAlignment(statusText, Pos.CENTER_RIGHT);
        statuStackPane.setMinSize(80, this.getMenuItemHeigth());

        Image lineImage = ImageEditor.ExchangeWidthHeight(line);
        ImageEditor imageEditor = new ImageEditor(0, 0, 0, 0);
        lineImage = imageEditor.ChangeHeight(lineImage, 40);
        ImageView VlineImageView = new ImageView(lineImage);

        ImageView lineImageView = new ImageView(this.line);

        GridPane gridPane = new GridPane();

        gridPane.add(IPStackPane, 0, 0);
        gridPane.add(VlineImageView, 1, 0);
        gridPane.add(statuStackPane, 2, 0);
        gridPane.add(lineImageView, 0, 1, 3, 1);

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setBlendMode(BlendMode.valueOf("DARKEN"));
        scrollPane.setStyle("-fx-background-color: transparent;-fx-border-color: transparent;");
        scrollPane.setMinSize(this.getMenuItemWidth() + 40, this.CHeight - 100);

        AnchorPane.setTopAnchor(gridPane, Double.valueOf(30));
        AnchorPane.setLeftAnchor(gridPane, Double.valueOf(this.getBWidth() / 2
                - this.getMenuItemWidth() / 2));
//        AnchorPane.setTopAnchor(this.cursorView, Double.valueOf(this.getMenuItemHeigth()));
//        AnchorPane.setLeftAnchor(this.cursorView, Double.valueOf(5));
        AnchorPane.setTopAnchor(scrollPane, Double.valueOf(35 + this.getMenuItemHeigth()));
        AnchorPane.setLeftAnchor(scrollPane, Double.valueOf(this.getBWidth() / 2
                - this.getMenuItemWidth() / 2) - 15);

        anchorPane.getChildren().remove(vBox);
        anchorPane.getChildren().remove(cursorView);
        anchorPane.getChildren().addAll(gridPane, scrollPane);
        this.addItem(new NetMenuItem());
        this.addItem(new NetMenuItem());
        this.addItem(new NetMenuItem());
        this.addItem(new NetMenuItem());
        this.addItem(new NetMenuItem());
        this.addItem(new NetMenuItem());
        this.addItem(new NetMenuItem());
        this.addItem(new NetMenuItem());
        this.addItem(new NetMenuItem());
        this.addItem(new NetMenuItem());
        this.addItem(new NetMenuItem());
        this.addItem(new NetMenuItem());
        this.addItem(new NetMenuItem());

    }

//    @Override
//    protected void changeCursorLocation(int index) {
//        AnchorPane.setTopAnchor(cursorView,
//                Double.valueOf(index * (this.getMenuItemHeigth() + 4)
//                        + this.getMenuItemHeigth()));
//    }
    @Override
    public void addItem(MenuItem menuItem) {
        menuItem.setOnMouseEntered(enterMouseEvent(menuItem, items.size()));
        menuItem.setOnMouseExited(exitMouseEvent(menuItem, items.size() - 1));
        items.add(menuItem);
        ImageView lineView = new ImageView(this.line);

        if (items.size() == 1) {
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

}
