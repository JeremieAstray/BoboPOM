
package boboPOM.code.controllers;

import boboPOM.config.Config;
import boboPOM.view.MainView;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

/**
 * @author yorlbgy
 */
public class Controller extends Parent {

    private ArrayList<KeyCode> c1, c2;
    private ArrayList<EventHandler> handlerList;
    public static MainView root;

    public Controller() {
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

        handlerList = new ArrayList<EventHandler>();
        root.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent ke) {
                c1Pe(ke);
                c2Pe(ke);
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

        });
        root.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent ke) {
                if (c1.contains(ke.getCode())) {
                    processEvent(new OpEvent(true, ke.getCode(), true));
                }
                if (c2.contains(ke.getCode())) {
                    processEvent(new OpEvent(false, ke.getCode(), true));
                }
            }

        });
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

}
