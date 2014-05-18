package boboPOM.view.main;

import boboPOM.code.playerside.Model;
import boboPOM.config.Config;
import javafx.event.Event;
import javafx.event.EventHandler;

import java.util.ArrayList;

/**
 * @author yorlbgy
 */
public class MainModel {

    private Model p1, p2;
    private ArrayList<EventHandler> handlerList;
    private boolean end;
    private boolean network;
    private boolean host;

    public MainModel(boolean host, boolean network) {
        Config.init();
        this.host = host;
        this.network = network;
        init(host, network);
        this.handlerList = new ArrayList<EventHandler>();
    }

    public Model getP1() {
        return p1;
    }

    public Model getP2() {
        return p2;
    }

    public boolean isNetwork() {
        return network;
    }

    public void winner(boolean p1) {
        if (p1) {
            this.p1.setWin(true);
        } else {
            this.p2.setWin(true);
        }
        if(host)
            this.p1.sp.getTimeline().stop();
        else
            this.p2.sp.getTimeline().stop();
        Config.controller.getNetgames().clear();
        Config.controller.gamerun = false;
        end = true;
    }

    public void SAtt(boolean p1, int add) {
        if (p1) {
            this.p1.setSBurst(true);
            this.p1.setBurstAtt(true, add);
        } else {
            this.p2.setSBurst(true);
            this.p2.setBurstAtt(true, add);
        }
    }

    public boolean isEnd() {
        return end;
    }

    public void again() {
//          init(false,false);
//          processEvent(new UpdataEvent("again"));
        this.end = false;
    }

    public void upData(UpdataEvent ue) {
        processEvent(ue);
    }

    private void init(boolean host, boolean network) {
        end = false;

        if (network) {
            if (host) {
                p1 = new Model(true, false);
                p2 = new Model(false, true);
            } else {
                p1 = new Model(true, true);
                p2 = new Model(false, false);
            }
        } else {
            p1 = new Model(true, false);
            p2 = new Model(false, false);
        }
        p1.setMainModel(this);
        p2.setMainModel(this);
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

    public synchronized void removeHandler(EventHandler eh) {
        if (handlerList.contains(eh)) {
            handlerList.remove(eh);
        }
    }

    public boolean isHost() {
        return this.host;
    }
}
