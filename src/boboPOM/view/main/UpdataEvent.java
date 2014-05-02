
package boboPOM.view.main;

import boboPOM.code.basic.Brick;
import boboPOM.code.basic.Bobo;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Node;

/**
 *
 * @author yorlbgy
 */
public final class UpdataEvent extends Event implements java.io.Serializable{

    private boolean p1;
    private ObservableList<Node> cops;
    private ObservableList<Node> comf;
    private String command;

    public UpdataEvent(EventType<? extends Event> arg0) {
        super(arg0);
    }

    public UpdataEvent(boolean p1,ObservableList<Node> cops,ObservableList<Node> comf) {
        this(EventType.ROOT);
        this.p1 = p1;
        this.cops = cops;
        this.comf = comf;
        this.command = "updata";
    }

    public UpdataEvent(String command) {
        this(EventType.ROOT);
        this.command = command;
    }

    public boolean isP1() {
        return p1;
    }

    public ObservableList<Node> getCops() {
        return cops;
    }

    public ObservableList<Node> getComf() {
        return comf;
    }

    public String getCommand() {
        return command;
    }
}
