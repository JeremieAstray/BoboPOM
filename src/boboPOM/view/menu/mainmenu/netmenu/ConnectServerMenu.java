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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * @author:feng
 */
public class ConnectServerMenu extends MenuBar {

    private int CWidth;
    private int CHeight;
    
    private int scrollUp = 0;

    private ScrollPane scrollPane;

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

        scrollPane = new ScrollPane(vBox);
        scrollPane.setBlendMode(BlendMode.valueOf("DARKEN"));
        scrollPane.setStyle("-fx-background-color: transparent;-fx-border-color: transparent;");
        scrollPane.setMinSize(this.getMenuItemWidth() + 40, this.CHeight - 100);

        AnchorPane.setTopAnchor(gridPane, Double.valueOf(30));
        AnchorPane.setLeftAnchor(gridPane, Double.valueOf(this.getBWidth() / 2
                - this.getMenuItemWidth() / 2));
        AnchorPane.setTopAnchor(scrollPane, Double.valueOf(35 + this.getMenuItemHeigth()));
        AnchorPane.setLeftAnchor(scrollPane, Double.valueOf(this.getBWidth() / 2
                - this.getMenuItemWidth() / 2) - 15);

        anchorPane.getChildren().remove(vBox);
        anchorPane.getChildren().remove(cursorView);
        anchorPane.getChildren().addAll(gridPane, scrollPane);

        this.addItem(new NetMenuItem());
        this.addItem(new NetMenuItem("1.1.1", false));
        this.addItem(new NetMenuItem("1.1.2", false));
        this.addItem(new NetMenuItem("1.1.8", false));
        this.addItem(new NetMenuItem("1.1.7", false));
        
//        this.addItem(new NetMenuItem("1.1.10", false));
//        this.addItem(new NetMenuItem("1.1.9", false));
//        this.addItem(new NetMenuItem("1.1.6", false));
//        
//        this.addItem(new NetMenuItem("1.1.11", false));
//        this.addItem(new NetMenuItem("1.1.12", false));
//        this.addItem(new NetMenuItem("1.1.13", false));
//        
//        this.addItem(new NetMenuItem("1.1.21", false));
//        this.addItem(new NetMenuItem("1.1.22", false));
//        this.addItem(new NetMenuItem("1.1.23", false));
//        
//        this.addItem(new NetMenuItem("1.1.31", false));
//        this.addItem(new NetMenuItem("1.1.32", false));
//        this.addItem(new NetMenuItem("1.1.33", false));

    }

    @Override
    public void addItem(MenuItem menuItem) {
        if (isAdd(menuItem)) {
            if (nowItemSelected == -1) {
                nowItemSelected = 0;
            }
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

    private boolean isAdd(MenuItem netMenuItem) {
        NetMenuItem menuItem = null;
        NetMenuItem menuItem2 = (NetMenuItem) netMenuItem;
        for (int i = 0; i < this.items.size(); i++) {
            menuItem = (NetMenuItem) this.items.get(i);
            if (menuItem.getIPText().equals(menuItem2.getIPText())) {
                if (menuItem.isConnected() == menuItem2.isConnected()) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        return true;
    }

    public String getSelectedItemIP() {
        return ((NetMenuItem) this.items.get(nowItemSelected)).getIPText();
    }

    @Override
    public void reset() {
        super.reset();
        this.vBox.getChildren().clear();
        this.items.clear();
        this.nowItemSelected = -1;
    }

    @Override
    public void DealKeyEvent(KeyCode t) {
        if (nowItemSelected != -1) {
            super.DealKeyEvent(t);
            int selected = this.getSelectedItem();
            if (selected > scrollUp + 6) {
                if (selected == this.items.size() - 1) {
                    scrollUp = this.items.size() - 7;
                }
                scrollUp ++;
                
            } else if (selected < scrollUp) {
                if (selected == 0) {
                    scrollUp = 0;
                } else {
                    scrollUp--;
                }
            }
            scrollPane.setVvalue(scrollUp * 1.0 / (this.items.size()-7));
        }
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }
    public VBox getVBox(){
        return this.vBox;
    }
}
