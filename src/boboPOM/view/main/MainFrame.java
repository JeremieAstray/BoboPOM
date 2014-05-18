package boboPOM.view.main;

import boboPOM.code.counters.CounterSet;
import boboPOM.code.playerside.Model;
import boboPOM.config.Config;
import boboPOM.view.Main;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

/**
 *
 * @author yorlbgy
 */
public class MainFrame extends StackPane implements EventHandler<UpdataEvent> {

    private MainModel model;
    private Model p1, p2;

    public MainFrame(MainModel model) {
        init(model);
        model.addHandler(this);
    }

    public final void setCounterSet(CounterSet cs) {
        cs.regCounters(this);
    }

    private void init(MainModel model) {
        this.model = model;
        this.p1 = model.getP1();
        this.p2 = model.getP2();
        p1.setMainFrame(this);
        p2.setMainFrame(this);
        p1.setPersonage(Config.Royde);
        p2.setPersonage(Config.Tio);
        ImageView bg = new ImageView(new Image(Main.class.getResourceAsStream("resources/images/background/background.png")));
        bg.setTranslateY(-5);
        this.setFocusTraversable(true);
        this.getChildren().addAll(p1.getPane(), p2.getPane(), bg);
        p1.regSet(this);
        p2.regSet(this);
        p1.getSp().play();
        p2.getSp().play();
    }

    public Model getP2() {
        return p2;
    }

    public Model getP1() {
        return p1;
    }

    @Override
    public void handle(UpdataEvent arg0) {
        switch(arg0.getCommand()){
            case "again":init(model);
            this.getChildren().get(0).requestFocus();
                break;
            case "clear":
                break;
            case "updata":
     
                break;
        }
    }

}
