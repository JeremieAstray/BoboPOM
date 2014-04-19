
package boboPOM.view.main;

import boboPOM.code.controllers.Controller;
import boboPOM.code.counters.CounterSet;
import boboPOM.code.playerside.Model;
import boboPOM.config.Config;
import boboPOM.view.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 *
 * @author yorlbgy
 */
public class MainFrame extends StackPane {

    private MainModel model;
    private Model p1, p2;

    public MainFrame(MainModel model) {
        init(model);

    }

    public final void setCounterSet(CounterSet cs) {
//        this.getChildren().add(cc.chains(player1.getTranslateX()+this.player1.getPosX(2), player2.getTranslateY()+this.player1.getPosY(2)));
//        this.getChildren().add(cc.chains(player1.getTranslateX()+this.player1.getPosX(5), player2.getTranslateY()+this.player1.getPosY(5)));
        cs.regCounters(this);
    }

    private void init(MainModel model) {
        this.model = model;
        this.p1 = model.getP1();
        this.p2 = model.getP2();
        p1.setMainFrame(this);
        p2.setMainFrame(this);
        //test
        p1.setPersonage(Config.Royde);
        p2.setPersonage(Config.Tio);

        ImageView bg = new ImageView(new Image(Main.class.getResourceAsStream("resources/images/background/background.png")));

        bg.setTranslateY(-5);
        Controller c = new Controller();
        c.addHandler(p1);
        c.addHandler(p2);
        this.setFocusTraversable(true);
        this.getChildren().addAll(c, p1.getPane(), p2.getPane(), bg);
        p1.regSet(this);
        p2.regSet(this);
        p1.getSp().play();
        p2.getSp().play();
        c.requestFocus();
    }
   
}
