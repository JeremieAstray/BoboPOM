/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boboPOM.controller;

import boboPOM.view.menu.BaseMenuBar;
import boboPOM.view.menu.DialogBox;
import boboPOM.view.menu.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author:feng
 */
public class MenuController implements Initializable {

    @FXML
    private BaseMenuBar baseMenuBar;
    @FXML
    private DialogBox dialogBox;

    @FXML
    private void keyChoose(KeyEvent e) {
        baseMenuBar.DealKeyEvent(e.getCode());
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        baseMenuBar.setSize(250, 70);
        MenuItem menuItem = new MenuItem("test", 200, 50);
        MenuItem menuItem2 = new MenuItem("test", 200, 50);
        MenuItem menuItem3 = new MenuItem("test", 200, 50);
        MenuItem menuItem4 = new MenuItem("test", 200, 50);
        baseMenuBar.addItem(menuItem);
        baseMenuBar.addItem(menuItem2);
        baseMenuBar.addItem(menuItem3);
        baseMenuBar.addItem(menuItem4);

    }

}
