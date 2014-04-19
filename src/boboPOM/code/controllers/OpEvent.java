package boboPOM.code.controllers;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;


/**
 *
 * @author yorlbgy
 */
public class OpEvent extends Event {

    private boolean p1;
    private KeyCode kc;
    private boolean released;
    public OpEvent(boolean p1, KeyCode kc) {
        super(EventType.ROOT);
        this.p1 = p1;
        this.kc = kc;
        released = false;
    }

    public OpEvent(boolean p1, KeyCode kc , boolean released){
        this(p1,kc);
        this.released = released;
    }
    
    public boolean isReleased(){
       return released;
    }
    public KeyCode getKc() {
        return kc;
    }

    public boolean isP1() {
        return p1;
    }
}
