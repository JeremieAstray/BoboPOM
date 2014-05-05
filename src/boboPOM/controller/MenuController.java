/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boboPOM.controller;

import boboPOM.config.Config;
import boboPOM.view.menu.mainmenu.DialogBox;
import boboPOM.view.menu.mainmenu.GameSetting;
import boboPOM.view.menu.mainmenu.MainMenuBar;
import boboPOM.view.menu.mainmenu.PlayerMenu;
import boboPOM.view.menu.mainmenu.SoloMenuBar;
import boboPOM.view.menu.mainmenu.netmenu.ConnectServerMenu;
import boboPOM.view.menu.mainmenu.netmenu.NetMenuBar;
import boboPOM.view.menu.mainmenu.netmenu.NetMenuItem;
import boboPOM.view.menu.mainmenu.netmenu.SocketMenu;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author:feng
 */
public class MenuController implements Initializable {

    @FXML
    private MainMenuBar mainMenuBar;
    @FXML
    private SoloMenuBar soloMenuBar;
    @FXML
    private NetMenuBar netMenuBar;
    @FXML
    private SocketMenu socketMenu;
    @FXML
    private DialogBox dialogBox;
    @FXML
    private GameSetting gameSetting;
    @FXML
    private ConnectServerMenu connectServerMenu;
    @FXML
    private PlayerMenu playerMenu;

    @FXML
    private void MainToNextInKey(KeyEvent e) {
        if (e.getCode() == KeyCode.K) {
            MainToNext();
        } else {
            mainMenuBar.DealKeyEvent(e.getCode());
        }
    }

    @FXML
    private void MainToNextInMouse(MouseEvent e) {
        if (e.isPrimaryButtonDown()) {
            MainToNext();
        }
    }

    private void MainToNext() {
        switch (mainMenuBar.getSelectedItem()) {
            case 0:
                soloMenuBar.setVisible(true);
                break;
            case 1:
                netMenuBar.setVisible(true);
                break;
            case 2:
                dialogBox.setVisible(true);
                dialogBox.gettTimeline().play();
                break;
            case 3:
                break;
            case 4:
                System.exit(1);
                break;
        }
        mainMenuBar.setVisible(false);
    }

    @FXML
    private void NetToOtherInKey(KeyEvent e) {
        if (e.getCode() == KeyCode.K) {
            NetToNext();
        } else if (e.getCode() == KeyCode.J) {
            NetReturn();
        } else {
            netMenuBar.DealKeyEvent(e.getCode());
        }
    }

    @FXML
    private void NetToOtherInMouse(MouseEvent e) {
        if (e.isPrimaryButtonDown()) {
            NetToNext();
        } else if (e.isSecondaryButtonDown()) {
            NetReturn();
        }
    }

    private void NetToNext() {
        switch (netMenuBar.getSelectedItem()) {
            case 0:
                socketMenu.setVisible(true);
                break;
            case 1:
                connectServerMenu.setVisible(true);
                break;
        }
        netMenuBar.setVisible(false);
    }

    private void NetReturn() {
        netMenuBar.setVisible(false);
        netMenuBar.reset();
        mainMenuBar.setVisible(true);
    }

    @FXML
    private void DialogNextInKey(KeyEvent e) {
        if (e.getCode() == KeyCode.K) {
            dialogBox.nextContent();
        } else if (e.getCode() == KeyCode.J) {
            DialogReturn();
        }
    }

    @FXML
    private void DialogNextInMouse(MouseEvent e) {
        if (e.isPrimaryButtonDown()) {
            dialogBox.nextContent();
        } else if (e.isSecondaryButtonDown()) {
            DialogReturn();
        }
    }

    private void DialogReturn() {
        if (dialogBox.isOver()) {
            dialogBox.setVisible(false);
            dialogBox.clearContent();
            mainMenuBar.setVisible(true);
        }
    }

    @FXML
    private void SoloToOtherInKey(KeyEvent e) {
        if (e.getCode() == KeyCode.K) {
            SoloToNext();
        } else if (e.getCode() == KeyCode.J) {
            SoloReturn();
        } else {
            soloMenuBar.DealKeyEvent(e.getCode());
            soloMenuBar.Deal2PKeyEvent(e.getCode());
        }
    }

    @FXML
    private void SoloToOtherInMouse(MouseEvent e) {
        if (e.isPrimaryButtonDown()) {
            SoloToNext();
        } else if (e.isSecondaryButtonDown()) {
            SoloReturn();
        }
    }

    private void SoloToNext() {

    }

    private void SoloReturn() {
        soloMenuBar.setVisible(false);
        soloMenuBar.reset();
        mainMenuBar.setVisible(true);
    }

    @FXML
    private void ConnectServerToOtherInKey(KeyEvent e) {
        if (e.getCode() == KeyCode.K) {
            ConnectServerToNext();
        } else if (e.getCode() == KeyCode.J) {
            ConnectServerReturn();
        } else {
            connectServerMenu.DealKeyEvent(e.getCode());
        }
    }

    @FXML
    private void ConnectServerToOtherInMouse(MouseEvent e) {
        if (e.isPrimaryButtonDown()) {
            ConnectServerToNext();
        } else if (e.isSecondaryButtonDown()) {
            ConnectServerReturn();
        }
    }

    private void ConnectServerToNext() {
        connectServerMenu.setVisible(false);
        playerMenu.setVisible(true);
    }

    private void ConnectServerReturn() {
        connectServerMenu.setVisible(false);
        connectServerMenu.reset();
        netMenuBar.setVisible(true);
    }
    
    @FXML
    private void PlayerMenuToOtherInKey(KeyEvent e){
        if(e.getCode() == KeyCode.K){
            PlayerMenuToNext();
        } else if(e.getCode() == KeyCode.J){
            PlayerMenuReturn();
        } else{
            playerMenu.DealKeyEvent(e.getCode());
        }
    }
    @FXML
    private void PlayerMenuToOtherInMouse(MouseEvent e){
        if(e.isPrimaryButtonDown()){
            PlayerMenuToNext();
        } else if(e.isSecondaryButtonDown()){
            PlayerMenuReturn();
        }
    }
    
    private void PlayerMenuToNext(){
        
    }
    
    private void PlayerMenuReturn(){
        playerMenu.setVisible(false);
        playerMenu.reset();
        netMenuBar.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        soloMenuBar.setVisible(false);
        netMenuBar.setVisible(false);
        socketMenu.setVisible(false);
        connectServerMenu.setVisible(false);
        dialogBox.setVisible(false);
        gameSetting.setVisible(false);
        playerMenu.setVisible(false);

        int width = Config.getSCREEN_WIDTH() / 2;
        int height = Config.getSCREEN_HEIGHT() / 2;

        mainMenuBar.setTranslateX(width - mainMenuBar.getBWidth() / 2);
        mainMenuBar.setTranslateY(height * 2 - mainMenuBar.getBHeight() - 10);

        soloMenuBar.setTranslateX(width - soloMenuBar.getBWidth() / 2);
        soloMenuBar.setTranslateY(height - soloMenuBar.getBHeight() / 2);

        netMenuBar.setTranslateX(width - netMenuBar.getBWidth() / 2);
        netMenuBar.setTranslateY(height - netMenuBar.getBHeight() / 2);
        socketMenu.setTranslateX(width - socketMenu.getSWidth() / 2);
        socketMenu.setTranslateY(height - socketMenu.getSHeight() / 2);

        dialogBox.setTranslateX(width - dialogBox.getDWidth() / 2);
        dialogBox.setTranslateY(height * 2 - dialogBox.getDHeight() - 30);

        gameSetting.setTranslateX(100);
        gameSetting.setTranslateY(100);

        //mainMenuBar.setVisible(false);
        connectServerMenu.setTranslateX(width - connectServerMenu.getBWidth() / 2);
        connectServerMenu.setTranslateY(height - connectServerMenu.getBHeight() / 2);

        playerMenu.setTranslateX(width - playerMenu.getBWidth() / 2);
        playerMenu.setTranslateY(height - playerMenu.getBHeight() / 2);
    }

}
