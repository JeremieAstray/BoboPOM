/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boboPOM.view.menu.mainmenu;

import boboPOM.view.menu.MenuBar;
import boboPOM.view.menu.MenuItem;

import javafx.scene.control.Control;

/**
 *
 * @author:feng
 */
public class MainMenuBar extends MenuBar {

    final private int itemNums = 5;

    public MainMenuBar() {
        this(200, 70, 150);
    }

    public MainMenuBar(int Width, int Height, int itemWidth) {
        super(Width, Height, itemWidth);
        init();
    }

    private void init() {
        String[] itemNames = {"单机模式", "网络对战", "游戏说明", "设置",
            "退出游戏"};
        MenuItem menuItem;
        for (int i = 0; i < itemNums; i++) {
            menuItem = new MenuItem(itemNames[i], this.getMenuItemWidth(),
                    this.getMenuItemHeigth());
            this.addItem(menuItem);
        }
    }
}
