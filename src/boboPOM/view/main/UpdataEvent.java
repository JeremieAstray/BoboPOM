
package boboPOM.view.main;

import boboPOM.code.basic.Brick;
import boboPOM.code.basic.Bobo;
import javafx.event.Event;
import javafx.event.EventType;

/**
 *
 * @author yorlbgy
 */
public final class UpdataEvent extends Event {

    private boolean p1;
    private Bobo[][] boboArray;
    private Bobo[] readyBobo, risingBobo;
    private Brick brick;
    private String command;

    public UpdataEvent(EventType<? extends Event> arg0) {
        super(arg0);
    }

    public UpdataEvent(boolean p1, Bobo[][] boboArray, Bobo[] readyBobo, Bobo[] risingBobo, Brick brick) {

        this(EventType.ROOT);
        this.p1 = p1;
        this.boboArray = boboArray;
        this.readyBobo = readyBobo;
        this.risingBobo = risingBobo;
        this.brick = brick;
    }

    public UpdataEvent(String command) {
        this(EventType.ROOT);
        this.command = command;
    }

    public Bobo[][] getBoboArray() {
        return boboArray;
    }

    public Bobo[] getReadyBobo() {
        return readyBobo;
    }

    public Bobo[] getRisingBobo() {
        return risingBobo;
    }

    public Brick getBrick() {
        return brick;
    }

    public boolean isP1() {
        return p1;
    }

    public String getCommand() {
        return command;
    }
}
