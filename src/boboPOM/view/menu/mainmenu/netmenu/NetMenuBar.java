/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boboPOM.view.menu.mainmenu.netmenu;

import boboPOM.view.menu.MenuBar;
import boboPOM.view.menu.MenuItem;

/**
 * @author:feng
 */
public class NetMenuBar extends MenuBar {
    private int itemNums = 2;

    public NetMenuBar() {
        this(200, 70, 150);
    }

    public NetMenuBar(int Width, int Height, int itemWidth) {
        super(Width, Height, itemWidth);
        init();
    }

    private void init() {
        String[] itemNames = {"开启服务", "连接服务"};
        MenuItem menuItem;
        for (int i = 0; i < itemNums; i++) {
            menuItem = new MenuItem(itemNames[i], this.getMenuItemWidth(),
                    this.getMenuItemHeigth());
            this.addItem(menuItem);
        }
    }
}
