/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boboPOM.view.menu.mainmenu;

import boboPOM.view.menu.MenuBar;
import boboPOM.view.menu.MenuItem;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author:feng
 */
public class PlayerMenu extends MenuBar {

    final private int itemNums = 9;

    public PlayerMenu() {
        this(250, 70, 200);
    }

    public PlayerMenu(int Width, int Height, int itemWidth) {
        super(Width, Height, itemWidth);
        init();
    }

    private void init() {
        String[] itemNames = {"罗伯兹", "罗伊德", "芙兰", "米歇尔", "约纳",
            "阿巴斯", "玛丽亚贝尔", "缇欧", "肯帕雷拉"};
        MenuItem menuItem;
        for (int i = 0; i < itemNums; i++) {
            menuItem = new MenuItem(itemNames[i], this.getMenuItemWidth(),
                    this.getMenuItemHeigth());
            this.addItem(menuItem);
        }

        Text title = new Text("角色选择");
        title.setFont(new Font(this.getMenuItemHeigth() - 15));
        StackPane stackPane = new StackPane(title);
        stackPane.setAlignment(title, Pos.CENTER);
        stackPane.setMinSize(this.getBWidth(), this.getMenuItemHeigth());
        this.addBHeight(this.getMenuItemHeigth());

        anchorPane.getChildren().add(stackPane);
        AnchorPane.setTopAnchor(stackPane, Double.valueOf(30));
        AnchorPane.setTopAnchor(cursorView, Double.valueOf(this.getMenuItemHeigth()));
        AnchorPane.setTopAnchor(getvBox(), Double.valueOf(30 + this.getMenuItemHeigth()));
    }

    @Override
    protected void changeCursorLocation(int index) {
        AnchorPane.setTopAnchor(cursorView,
                Double.valueOf(index * (this.getMenuItemHeigth() + 6) - 5
                        + this.getMenuItemHeigth()));
    }
}
