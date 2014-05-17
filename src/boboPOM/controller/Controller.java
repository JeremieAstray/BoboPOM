package boboPOM.controller;

import boboPOM.config.Config;
import boboPOM.net.MsgQueue;
import boboPOM.net.SocketLink;
import boboPOM.net.encoding.FirstMessage;
import boboPOM.net.encoding.UpdataMessage;
import boboPOM.view.MainView;
import boboPOM.view.MenuView;
import boboPOM.view.main.UpdataEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jeremie on 14-3-5.
 */
public class Controller implements Initializable {

    @FXML
    private MainView mainView;
    @FXML
    private MenuView menuView;
    private ArrayList<KeyCode> c1, c2;
    private ArrayList<EventHandler> handlerList;
    private MsgQueue<Object> gameMsg;
    private SocketLink socketLink;
    private boolean getp2 = false;

    @FXML
    private void keyPressed(KeyEvent event) {
        c1Pe(event);
        c2Pe(event);
    }

    private synchronized void c1Pe(KeyEvent ke) {
        if (c1.contains(ke.getCode())) {
            processEvent(new OpEvent(true, ke.getCode()));
        }
    }

    private synchronized void c2Pe(KeyEvent ke) {
        if (c2.contains(ke.getCode())) {
            processEvent(new OpEvent(false, ke.getCode()));
        }
    }

    @FXML
    private void keyRelease(KeyEvent event) {
        if (c1.contains(event.getCode())) {
            processEvent(new OpEvent(true, event.getCode(), true));
        }
        if (c2.contains(event.getCode())) {
            processEvent(new OpEvent(false, event.getCode(), true));
        }
    }

    /**
     * 初始化controller
     *
     * @param location
     * @param resources
     */
    private Integer p2 = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        handlerList = new ArrayList<EventHandler>();
        Config.p1Controller = new ArrayList<>();
        c1 = Config.p1Controller;
        Config.p2Controller = new ArrayList<>();
        c2 = Config.p2Controller;
        //test
        c1.add(KeyCode.A);
        c1.add(KeyCode.D);
        c1.add(KeyCode.S);
        c1.add(KeyCode.J);
        c1.add(KeyCode.U);
        c1.add(KeyCode.I);
        c1.add(KeyCode.O);
        c1.add(KeyCode.SPACE);
        c2.add(KeyCode.LEFT);
        c2.add(KeyCode.RIGHT);
        c2.add(KeyCode.DOWN);
        c2.add(KeyCode.NUMPAD1);
        c2.add(KeyCode.NUMPAD4);
        c2.add(KeyCode.NUMPAD5);
        c2.add(KeyCode.NUMPAD6);
        c2.add(KeyCode.NUMPAD0);
        Config.controller = this;
    }

    public void initGames(int p1, int p2) {
        menuView.setVisible(false);
        mainView.init(true, false/*Config.network*/);
        mainView.setFocusTraversable(true);
        mainView.requestFocus();
        System.out.println(p1 + " " + p2);
        mainView.setPersonages(p1, p2);
        this.addHandler(mainView.getMainFrame().getP1());
        this.addHandler(mainView.getMainFrame().getP2());
    }

    public void initNetGames(boolean host, int p1, SocketLink socketLink, MsgQueue<Object> regamesMsg) {
        socketLink.send(new Integer(p1));
        this.socketLink = socketLink;
        this.gameMsg = regamesMsg;
        Timer t = new Timer(true);
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!gameMsg.isEmpty()) {
                    Object o = gameMsg.recv();
                    if (o instanceof Integer) {
                        p2 = (Integer) o;
                        getp2 = true;
                    } else if (o instanceof FirstMessage) {
                        mainView.getMainFrame().getP2().recv(o);
                    }else if(o instanceof UpdataMessage){
                        mainView.getMainFrame().getP2().recv(o);
                    }
                }
            }
        }, 0, 40);
        menuView.setVisible(false);
        mainView.init(host, Config.network);
        mainView.setFocusTraversable(true);
        mainView.requestFocus();
        while (!getp2) {
        }
        mainView.setPersonages(p1, p2);
        this.addHandler(mainView.getMainFrame().getP1());
        this.addHandler(mainView.getMainFrame().getP2());
    }

    private void processEvent(Event e) {
        ArrayList<EventHandler> list;
        synchronized (handlerList) {
            list = (ArrayList<EventHandler>) handlerList.clone();
        }
        for (EventHandler eh : list) {
            eh.handle(e);
        }
    }

    public synchronized void addHandler(EventHandler eh) {
        if (!handlerList.contains(eh)) {
            handlerList.add(eh);
        }
    }

    public void send(Object o) {
        socketLink.send(o);
    }
}
