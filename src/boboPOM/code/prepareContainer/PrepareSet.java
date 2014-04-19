
package boboPOM.code.prepareContainer;

import boboPOM.code.basic.Bobo;
import boboPOM.code.basic.Brick;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 *
 * @author yorlbgy
 */
public class PrepareSet {

    private final boolean p1;
    private final QpPane qp;
    private final RpPane rp;

    public PrepareSet(boolean p1) {
        this.p1 = p1;
        BoboFactory.init();
        qp = new QpPane(p1);
        rp = new RpPane(p1);
    }

    public void regPrepareSet(Pane p) {
        p.getChildren().addAll(qp, rp);
    }

    public Brick takeBrick() {
        return qp.take();
    }

    public ArrayList<Bobo> takeBobos() {
        return rp.take();
    }

}
