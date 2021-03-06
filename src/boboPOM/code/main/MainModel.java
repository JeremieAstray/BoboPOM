package boboPOM.code.main;

import boboPOM.code.playerside.Model;
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
    private boolean host;

    public MainModel(boolean host, boolean network) {
        this.host = host;
        init(host, network);
        this.handlerList = new ArrayList<EventHandler>();
    }

    public Model getP1() {
        return p1;
    }

    public Model getP2() {
        return p2;
    }

    public void winner(boolean p1) {
        if (p1) {
            this.p1.setWin(true);
        } else {
            this.p2.setWin(true);
        }
        end = true;
//        System.out.println(end);
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

    public void setEnd(boolean b) {
        this.end = b;
    }
}
