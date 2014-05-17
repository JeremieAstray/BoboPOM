
package boboPOM.code.counters;

import boboPOM.code.playerside.Model;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

/**
 *
 * @author yorlbgy
 */
public class CounterSet {

    private final boolean p1;
    private final CPCounter cpc;
    private final ChainCounter chc;
    private final LineCounter lc;

    private final ArrayList<StackPane> usedPane;

    public CounterSet(boolean p1) {
        this.p1 = p1;
        cpc = new CPCounter(p1);
        chc = new ChainCounter(p1);
        chc.setCPCounter(cpc);
        lc = new LineCounter(p1);
        usedPane = new ArrayList<>();
    }
// network use
    public CPCounter getCpc() {
        return cpc;
    }

    public void setNowCP(int cp){
        this.cpc.setNowCP(cp);
    }
    
    public LineCounter getLc() {
        return lc;
    }
    
    public void setNowLines(int lines){
        this.lc.setNowLines(lines);
    }
    // network use

    
    public synchronized int useCP() {
        return cpc.useCP();
    }

    public synchronized boolean isShinning() {
        return cpc.isShinning();
    }

    public void addCP(int add) {
        cpc.addCP(add);
    }

    public synchronized StackPane chains(double x, double y,boolean newChain) {
        StackPane sp;
        if (newChain) {
            sp = chc.chains(x, y);
        } else {
            sp =  spClone(usedPane.get(usedPane.size() - 1));
            sp.setTranslateX(x);
            sp.setTranslateY(y);
        }
        usedPane.add(sp);
        return sp;
    }

    private StackPane spClone(StackPane sp){
        StackPane newSp = new StackPane();
        for(int i=0;i<sp.getChildren().size();i++){
            ImageView iv = (ImageView)sp.getChildren().get(i);
            ImageView newIv = new ImageView();
           newIv.setImage(iv.getImage());
           newIv.setVisible(iv.isVisible());
           newIv.setTranslateX(iv.getTranslateX());
           newIv.setTranslateY(iv.getTranslateY());
           newSp.getChildren().add(newIv);
        }
        return newSp;
    }
    public void spClear(Model m){
        m.getMainFrame().getChildren().removeAll(usedPane);
        usedPane.clear();
    }
    public void down() {
        chc.down();
    }

    public synchronized int useLines() {
        return lc.useLines();
    }

    public synchronized void addLines(int add) {
        lc.addLines(add);
    }

    public void regCounters(Pane p) {
        p.getChildren().addAll(cpc, chc, lc);
    }

}
