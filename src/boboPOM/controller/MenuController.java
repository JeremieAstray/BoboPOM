/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boboPOM.controller;

import boboPOM.config.Config;
import boboPOM.net.BroadcastSession;
import boboPOM.net.MsgQueue;
import boboPOM.view.menu.mainmenu.*;
import boboPOM.view.menu.mainmenu.netmenu.ConnectServerMenu;
import boboPOM.view.menu.mainmenu.netmenu.NetMenuBar;
import boboPOM.view.menu.mainmenu.netmenu.NetMenuItem;
import boboPOM.view.menu.mainmenu.netmenu.SocketMenu;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

/**
 * @author:feng
 */
public class MenuController implements Initializable {


    private BroadcastSession broadcastSession;
    private Timeline broadcastListenerTimeline;
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
                gameSetting.setVisible(true);
                break;
            case 4:
                System.exit(1);
                break;
        }
        mainMenuBar.setVisible(false);
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
                openServer();
                socketMenu.setVisible(true);
                break;
            case 1:
                MsgQueue<String> broadcastmsgs = new MsgQueue<String>();
                connectServer(broadcastmsgs);
                broadcastListenerTimeline = new Timeline();
                broadcastListenerTimeline.setCycleCount(Timeline.INDEFINITE);
                KeyFrame kf = new KeyFrame(Config.ANIMATION_TIME, new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        boolean flag = false;
                        if (!broadcastmsgs.isEmpty()) {
                            flag = true;
                            String recv = broadcastmsgs.recv();
                            String[] recvs = recv.split(" ");
                            boolean connected = false;
                            if (recvs.length != 2)
                                flag = false;
                            else if ("notconnected".equals(recvs[1]))
                                connected = false;
                            else if ("isconnected".equals(recvs[1]))
                                connected = true;
                            else
                                flag = false;
                            if (flag) {
                                NetMenuItem netMenuItem = new NetMenuItem(recvs[0], connected);
                                connectServerMenu.addItem(netMenuItem);
                            }else{
                                System.err.println(recv);
                            }
                        }
                    }
                });
                broadcastListenerTimeline.getKeyFrames().add(kf);
                broadcastListenerTimeline.play();
                connectServerMenu.setVisible(true);
                break;
        }
        netMenuBar.setVisible(false);
    }

    private String openServer() {
        broadcastSession = new BroadcastSession(Config.PORT);
        Thread broadcastSessionThread = new Thread(broadcastSession);
        broadcastSessionThread.start();
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }

    private void connectServer(MsgQueue<String> broadcastmsgs) {
        broadcastSession = new BroadcastSession(Config.PORT, broadcastmsgs);
        Thread broadcastSessionThread = new Thread(broadcastSession);
        broadcastSessionThread.start();
    }

    private void NetReturn() {
        netMenuBar.setVisible(false);
        netMenuBar.reset();
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
        broadcastSession.setRun(false);
        broadcastListenerTimeline.stop();
        connectServerMenu.setVisible(false);
        connectServerMenu.reset();
        netMenuBar.setVisible(true);
    }

    @FXML
    private void SocketToOtherInKey(KeyEvent e) {
        if (e.getCode() == KeyCode.J) {
            SocketReturn();
        } else if (e.getCode() == KeyCode.K) {
            SocketToNext();
        }
    }

    @FXML
    private void SocketToOtherInMouse(MouseEvent e) {
        if (e.isSecondaryButtonDown()) {
            SocketReturn();
        } else if (e.isPrimaryButtonDown()) {
            SocketToNext();
        }
    }

    private void SocketReturn() {
        if (!socketMenu.isConnected()) {
            socketMenu.setVisible(false);
            netMenuBar.setVisible(true);
        }
        broadcastSession.setRun(false);
    }

    private void SocketToNext() {
        if (socketMenu.isConnected()) {
            socketMenu.setVisible(false);
            playerMenu.setVisible(true);
        }
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
    private void GameSettingReturnInKey(KeyEvent e) {
        if (e.getCode() == KeyCode.J) {
            GameSettingReturn();
        }
    }

    @FXML
    private void GameSettingReturnInMouse(MouseEvent e) {
        if (e.isSecondaryButtonDown()) {
            GameSettingReturn();
        }
    }

    private void GameSettingReturn() {
        gameSetting.setVisible(false);
        mainMenuBar.setVisible(true);
    }

    @FXML
    private void PlayerMenuToOtherInKey(KeyEvent e) {
        if (e.getCode() == KeyCode.K) {
            PlayerMenuToNext();
        } else if (e.getCode() == KeyCode.J) {
            PlayerMenuReturn();
        } else {
            playerMenu.DealKeyEvent(e.getCode());
        }
    }

    @FXML
    private void PlayerMenuToOtherInMouse(MouseEvent e) {
        if (e.isPrimaryButtonDown()) {
            PlayerMenuToNext();
        } else if (e.isSecondaryButtonDown()) {
            PlayerMenuReturn();
        }
    }

    private void PlayerMenuToNext() {

    }

    private void PlayerMenuReturn() {
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
        //mainMenuBar.setVisible(false);

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

        connectServerMenu.setTranslateX(width - connectServerMenu.getBWidth() / 2);
        connectServerMenu.setTranslateY(height - connectServerMenu.getBHeight() / 2);

        dialogBox.setTranslateX(width - dialogBox.getDWidth() / 2);
        dialogBox.setTranslateY(height * 2 - dialogBox.getDHeight() - 30);

        gameSetting.setTranslateX(width - gameSetting.getGWidth() / 2);
        gameSetting.setTranslateY(height - gameSetting.getGHeight() / 2);

        playerMenu.setTranslateX(width - playerMenu.getBWidth() / 2);
        playerMenu.setTranslateY(height - playerMenu.getBHeight() / 2);
    }

}
