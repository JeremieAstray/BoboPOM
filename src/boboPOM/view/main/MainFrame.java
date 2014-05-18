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
                Model m;
             if(arg0.isP1())
                 m = this.p1;
             else m = this.p2;
             override(m,arg0);
                break;
        }
    }
//network use
    private synchronized void override(Model m, UpdataEvent arg0) {
         ObservableList<Node> list = this.getChildren();
         ArrayList<Node> al = new ArrayList<>();
         if(m.isP1())
         for(Node n: list){
             if(n.getTranslateX()<0)
                 al.add(n);
         }
         else 
            for(Node n: list){
             if(n.getTranslateX()>0)
                 al.add(n);
         }
         al.remove(m.getCounterSet().getCpc());
         al.remove(m.getCounterSet().getLc());
         al.remove(m.getPrepareSet().getQp());
         al.remove(m.getPrepareSet().getRp());
         al.remove(m.getPane());
         list.removeAll(al);
         if(!arg0.getComf().isEmpty())
         list.addAll(arg0.getComf());
         
         list = m.getPane().getChildren();
         al.clear();
         al.addAll(list);
         al.remove(list.get(0));
         al.remove(list.get(1));
         list.removeAll(al);
         list.addAll(arg0.getCops());
         
         list = m.getPrepareSet().getQp().getChildren();
         list.clear();
         list.addAll(arg0.getCoqp());
         
         list = m.getPrepareSet().getRp().getChildren();
         list.clear();
         list.addAll(arg0.getCorp());
         
         m.getCounterSet().setNowCP(arg0.getCp());
         m.getCounterSet().setNowLines(arg0.getLines());
    }
   
}
