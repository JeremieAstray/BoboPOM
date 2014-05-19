/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boboPOM.controller;

import boboPOM.config.Config;
import boboPOM.net.BroadcastSession;
import boboPOM.net.MsgQueue;
import boboPOM.net.SocketLink;
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
    private Timeline ConnectServerListenerTimeline;
    private Timeline ServerListenerTimeline;
    private Timeline PrepareListenerTimeline;
    private MsgQueue<String> status;
    private MsgQueue<Object> gamesMsg;
    private String currentStatus = null;
    private boolean oppositeReady = false;
    private SocketLink socketLink;

    private int menuMark = 0;

    private boolean host = false;
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
    private PrepareBar prepareBar;
    @FXML
    private SoloPrepareBar soloPrepareBar;

    @FXML
    private void MenuInKeyEvent(KeyEvent e) {
        if (e.getCode() == KeyCode.K) {
            MenuToNext(true);
        } else if (e.getCode() == KeyCode.J) {
            MenuReturn();
        } else {
            MenuOther(e.getCode());
        }
    }

    @FXML
    private void MenuInMouseEvent(MouseEvent e) {
        if (e.isPrimaryButtonDown()) {
            MenuToNext(false);
        } else if (e.isSecondaryButtonDown()) {
            MenuReturn();
        }
    }

    private void MenuToNext(boolean isKey) {
        if (menuMark != 3 && menuMark != 1 && menuMark != 21 && menuMark != 22
                && menuMark != 4) {
            Config.effectMedia.play(1);
        }
        if (menuMark < 10) {
            switch (menuMark) {
                case 0:  //主菜单
                    if (mainMenuBar.getvBox().isHover() || isKey) {
                        MainToNext();
                        menuMark = menuMark * 10 + mainMenuBar.getSelectedItem() + 1;
                    }
                    break;
                case 1:  //单机模式菜单
                    if (soloMenuBar.getvBox().isHover() || isKey) {
                        if (soloPrepareBar.isPrepared() != 0) {
                            Config.effectMedia.play(1);
                            soloPrepareBar.set1PPrepared(true);
                            soloMenuBar.setMouseEffect(false);
                            if (soloPrepareBar.isPrepared() == 2) {
                                SoloToNext(soloMenuBar.getSelectedItem(),
                                        soloMenuBar.getItemSelected2P());
                            }
                        }
                    }
                    break;
                case 2:  //网络对战菜单
                    if (netMenuBar.getvBox().isHover() || isKey) {
                        NetToNext();
                    }
                    break;
                case 3:  //游戏说明对话框
                    DialogToNext();
                    break;
                case 4:  //游戏设置选项
                    break;
            }
        } else if (menuMark > 10 && menuMark < 100) {
            switch (menuMark) {
                case 22:  //连接服务菜单
                    if (connectServerMenu.getSelectedItem() != -1
                            && (connectServerMenu.getvBox().isHover() || isKey)) {
                        NetMenuItem netMenuItem = connectServerMenu.getSelectedMenuItem();
                        if (!netMenuItem.isConnected()) {
                            ConnectServerToNext(netMenuItem.getIPText());
                        } else {
                            System.out.println("已连接");
                        }
                    }
                    break;
            }
        } else if (menuMark > 100 && menuMark < 1000) {
            switch (menuMark) {
                case 210:
                case 220:
                    if (playerMenu.getvBox().isHover() || isKey) {
                        PlayerMenuToNext();
                        menuMark *= 10;
                    }
                    break;
            }
        }
    }

    private void MenuReturn() {
        if (menuMark != 3) {
            Config.effectMedia.play(2);
        }
        if (menuMark > 0 && menuMark < 10) {
            switch (menuMark) {
                case 1: //单机模式菜单
                    if (soloPrepareBar.isPrepared() == 0) {
                        soloPrepareBar.set1PPrepared(false);
                        soloMenuBar.setMouseEffect(true);
                    } else {
                        SoloReturn();
                        menuMark /= 10;
                    }
                    break;
                case 2:  //网络对战菜单
                    NetReturn();
                    menuMark /= 10;
                    break;
                case 3: //游戏说明对话框
                    DialogToNext();
                    break;
                case 4: //游戏设置选项
                    GameSettingReturn();
                    menuMark /= 10;
                    break;
            }
        } else if (menuMark > 10 && menuMark < 100) {
            switch (menuMark) {
                case 21: //开启服务面板
                    SocketReturn(false);
                    menuMark /= 10;
                    break;
                case 22:  //连接服务菜单
                    ConnectServerReturn();
                    menuMark /= 10;
                    break;
            }
        } else if (menuMark > 100 && menuMark < 1000) {
            switch (menuMark) { //游戏角色选择画面
                case 210:
                case 220:
                    PlayerMenuReturn();
                    break;
            }
            menuMark /= 100;
        } else if (menuMark > 1000) {
            PrepareBarReturn();
            menuMark /= 10;
        }
    }

    private void MenuOther(KeyCode keyCode) {
        if (menuMark < 10) {
            switch (menuMark) {
                case 0:  //主菜单
                    mainMenuBar.DealKeyEvent(keyCode);
                    break;
                case 1:  //单机模式菜单
                    int prepare = soloPrepareBar.isPrepared();
                    if (keyCode == keyCode.NUMPAD2) {
                        if (prepare != 1) {
                            soloPrepareBar.set2PPrepared(true);
                            Config.effectMedia.play(1);
                            if (soloPrepareBar.isPrepared() == 2) {
                                SoloToNext(soloMenuBar.getSelectedItem(),
                                        soloMenuBar.getItemSelected2P());
                            }
                        }
                    } else if (keyCode == keyCode.NUMPAD1 && prepare == 1) {
                        soloPrepareBar.set2PPrepared(false);
                        Config.effectMedia.play(2);
                    } else {
                        if (prepare != 0) {
                            soloMenuBar.DealKeyEvent(keyCode);
                        }
                        if (prepare != 1) {
                            soloMenuBar.Deal2PKeyEvent(keyCode);
                        }
                    }
                    break;
                case 2:  //网络对战菜单
                    netMenuBar.DealKeyEvent(keyCode);
                    break;
            }
        } else if (menuMark > 10 && menuMark < 100) {
            switch (menuMark) {
                case 22:  //连接服务菜单
                    if (keyCode == keyCode.ENTER) {
                        if (connectServerMenu.ButtonActionDeal()) {
                            Config.effectMedia.play(1);
                            ConnectServerToNext(connectServerMenu.getTextFieldIP());
                        }
                    } else {
                        connectServerMenu.DealKeyEvent(keyCode);
                    }
                    break;
            }
        } else if (menuMark > 100 && menuMark < 1000) {
            switch (menuMark) {
                case 210:
                case 220:
                    playerMenu.DealKeyEvent(keyCode);
                    break;
            }
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

    private void SoloToNext(int p1, int p2) {
        Config.bgmMedia.stopMusic();
        Config.controller.initGames(p1, p2);
    }

    private void SoloReturn() {
        soloMenuBar.setVisible(false);
        soloMenuBar.reset();
        soloPrepareBar.reset();
        mainMenuBar.setVisible(true);
    }

    private void NetToNext() {

        switch (netMenuBar.getSelectedItem()) {
            case 0:
                openServer();
                SocketToNext();
                break;
            case 1:
                connectServerMenu.setVisible(true);
                connectServerMenu.getListenSocketTimeTimeline().play();
                menuMark = menuMark * 10 + 2;
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
                            if (recvs.length != 2) {
                                flag = false;
                            } else if ("notconnected".equals(recvs[1])) {
                                connected = false;
                            } else if ("isconnected".equals(recvs[1])) {
                                connected = true;
                            } else {
                                flag = false;
                            }
                            if (flag) {
                                NetMenuItem netMenuItem = new NetMenuItem(recvs[0], connected);
                                connectServerMenu.addItem(netMenuItem);
                            } else {
                                System.err.println(recv);
                            }
                        }
                    }
                });
                broadcastListenerTimeline.getKeyFrames().add(kf);
                broadcastListenerTimeline.play();
                break;
        }
        netMenuBar.setVisible(false);
    }

    private String openServer() {
        status = new MsgQueue<String>();
        gamesMsg = new MsgQueue<>();
        socketLink = new SocketLink(status, gamesMsg, Config.PORT);
        Thread socketLinkThread = new Thread(socketLink);
        socketLinkThread.start();
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

    private void ConnectServerToNext(String ip) {
        status = new MsgQueue<String>();
        gamesMsg = new MsgQueue<>();
        socketLink = new SocketLink(status, gamesMsg, Config.PORT, ip);
        Thread socketLinkThread = new Thread(socketLink);
        socketLinkThread.start();
        ConnectServerListenerTimeline = new Timeline();
        ConnectServerListenerTimeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(Config.ANIMATION_TIME, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!status.isEmpty()) {
                    String recv = status.recv();
                    if ("连接成功".equals(recv)) {
                        connectServerMenu.setVisible(false);
                        playerMenu.setVisible(true);
                        menuMark *= 10;
                        currentStatus = recv;
                        broadcastSession.setConnected(true);
                        broadcastListenerTimeline.stop();
                        Config.effectMedia.play(1);
                        ListenPeerPrepare();
                        host = false;
                    } else if ("连接断开".equals(recv)) {
                        currentStatus = recv;
                        ConnectServerReturn();
                        prepareBar.reset();
                        playerMenu.setVisible(false);
                        menuMark = 2;
                        ConnectServerListenerTimeline.stop();
                    } else if ("端口被占用".equals(recv)) {
                        currentStatus = recv;
                    }
                }
            }
        });
        ConnectServerListenerTimeline.getKeyFrames().add(kf);
        ConnectServerListenerTimeline.play();
    }

    //客户端返回
    private void ConnectServerReturn() {
        if (currentStatus != null && !socketLink.isServer() && socketLink.isRun()) {
            socketLink.close();
        }
        broadcastSession.setRun(false);
        broadcastListenerTimeline.stop();
        connectServerMenu.setVisible(false);
        connectServerMenu.reset();
        netMenuBar.setVisible(true);
    }

    //服务端返回
    private void SocketReturn(boolean clientReturn) {
        socketMenu.setVisible(false);
        netMenuBar.setVisible(true);
        broadcastSession.setRun(false);
        if (!socketLink.isDone() || clientReturn) {
            if (clientReturn) {
                socketLink.closeserver();
            } else {
                socketLink.close();
            }
        }
    }

    private void SocketToNext() {
        ServerListenerTimeline = new Timeline();
        ServerListenerTimeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(Config.ANIMATION_TIME, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!status.isEmpty()) {
                    String recv = status.recv();
                    if ("连接成功".equals(recv)) {
                        currentStatus = recv;
                        ListenPeerPrepare();
                        menuMark = menuMark * 10;
                        broadcastSession.setConnected(true);
                        socketMenu.setVisible(false);
                        playerMenu.setVisible(true);
                        host = true;
                    } else if ("连接断开".equals(recv)) {
                        currentStatus = recv;
                        SocketReturn(true);
                        broadcastSession.setConnected(false);
                        prepareBar.reset();
                        playerMenu.setVisible(false);
                        menuMark = 2;
                        ServerListenerTimeline.stop();
                    } else if ("端口被占用".equals(recv)) {
                        currentStatus = recv;
                        broadcastSession.setConnected(false);
                        if (socketMenu.isVisible()) {
                            menuMark /= 10;
                            SocketReturn(false);
                        }
                        System.out.println(recv);
                    } else if ("等待连接中".equals(recv)) {
                        socketMenu.setVisible(true);
                        menuMark = menuMark * 10 + 1;
                    }
                }
            }
        });
        ServerListenerTimeline.getKeyFrames().add(kf);
        ServerListenerTimeline.play();
    }

    private void DialogToNext() {
        if (dialogBox.isOver()) {
            dialogBox.setVisible(false);
            dialogBox.clearContent();
            dialogBox.setOver(false);
            mainMenuBar.setVisible(true);
            menuMark /= 10;
        } else {
            dialogBox.nextContent();
        }
    }

    private void GameSettingReturn() {
        gameSetting.setVisible(false);
        mainMenuBar.setVisible(true);
    }

    private void PlayerMenuToNext() {
        socketLink.send("ready");
        if (!prepareBar.isPeerPrepared()) {
            prepareBar.setOwnPrepared(true);
            playerMenu.setVisible(false);
        } else {
            GameStar(playerMenu.getSelectedItem());//开始游戏
        }
    }

    private void PlayerMenuReturn() {
        broadcastSession.setRun(false);
        if (broadcastListenerTimeline != null) {
            broadcastListenerTimeline.stop();
        }
        prepareBar.reset();
        if (socketLink.isRun()) {
            socketLink.close();
        }
        playerMenu.setVisible(false);
        playerMenu.reset();
        netMenuBar.setVisible(true);
    }

    private void PrepareBarReturn() {
        if (prepareBar.isOwnPrepared()) {
            socketLink.send("noReady");
            prepareBar.setOwnPrepared(false);
            playerMenu.setVisible(true);
        }
    }

    private void ListenPeerPrepare() {
        PrepareListenerTimeline = new Timeline();
        PrepareListenerTimeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(Config.ANIMATION_TIME, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!gamesMsg.isEmpty()) {
                    Object o = gamesMsg.recv();
                    if (o instanceof String) {
                        String recv = (String) o;
                        System.out.println(recv);
                        if ("ready".equals(recv)) {
                            currentStatus = recv;
                            if (!prepareBar.isOwnPrepared()) {
                                prepareBar.setPeerPrepared(true);
                            } else {
                                GameStar(playerMenu.getSelectedItem());
                            }
                        } else if ("noReady".equals(recv)) {
                            currentStatus = recv;
                            prepareBar.setPeerPrepared(false);
                        }
                    }
                }
            }
        });
        PrepareListenerTimeline.getKeyFrames().add(kf);
        PrepareListenerTimeline.play();
    }

    private void GameStar(int p1) {
        this.prepareBar.setVisible(false);
        this.playerMenu.setVisible(false);
        this.menuMark = 0;
        broadcastSession.setRun(false);
        if (broadcastListenerTimeline != null) {
            broadcastListenerTimeline.stop();
        }
        if (ConnectServerListenerTimeline != null) {
            ConnectServerListenerTimeline.stop();
        }
        if (ServerListenerTimeline != null) {
            ServerListenerTimeline.stop();
        }
        if (PrepareListenerTimeline != null) {
            PrepareListenerTimeline.stop();
        }
        Config.bgmMedia.stopMusic();
        System.out.println("开始游戏");
        Config.network = true;
        Config.controller.initNetGames(host, p1, socketLink, gamesMsg);
    }

    public void ReturnToMenu() {
        mainMenuBar.reset();
        this.soloPrepareBar.reset();
        this.menuMark = 0;
        this.soloMenuBar.setVisible(false);
        mainMenuBar.setVisible(true);
        Config.bgmMedia.playMusic(2, true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Config.menuController = this;
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

        connectServerMenu.setTranslateX(width - connectServerMenu.getBWidth() / 2);
        connectServerMenu.setTranslateY(height - connectServerMenu.getBHeight() / 2);

        connectServerMenu.getButton().setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                if (connectServerMenu.ButtonActionDeal()) {
                    Config.effectMedia.play(1);
                    ConnectServerToNext(connectServerMenu.getTextFieldIP());
                }
            }
        });

        dialogBox.setTranslateX(width - dialogBox.getDWidth() / 2);
        dialogBox.setTranslateY(height * 2 - dialogBox.getDHeight() - 30);

        gameSetting.setTranslateX(width - gameSetting.getGWidth() / 2);
        gameSetting.setTranslateY(height - gameSetting.getGHeight() / 2);

        playerMenu.setTranslateX(width - playerMenu.getBWidth() / 2);
        playerMenu.setTranslateY(height - playerMenu.getBHeight() / 2);

        Config.bgmMedia.playMusic(2, true);
    }

}
