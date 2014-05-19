package boboPOM.code.prepareContainer;

import boboPOM.code.basic.Bobo;
import boboPOM.config.Config;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

/**
 * @author yorlbgy
 */
public class RpPane extends StackPane {

    private boolean p1;
    private ArrayList<Bobo> bobos;
    private QueueList<ArrayList<Bobo>> source;

    public RpPane(boolean p1) {
        this.p1 = p1;
        bobos = new ArrayList<>();
        if (p1) {
            source = BoboFactory.rp1;
            this.setTranslateX(-243);
        } else {
            source = BoboFactory.rp2;
            this.setTranslateX(237);
        }
        this.setTranslateY(253);
        fill();
    }

    private void fill() {
        this.getChildren().removeAll(bobos);
        bobos = source.take();
        if (source.size() <= 2) {
            BoboFactory.makeBobos();
        }
        layout(bobos);
        this.getChildren().addAll(bobos);
    }

    private void layout(ArrayList<Bobo> bobos) {
        for (int i = 0; i < 7; i++) {
            bobos.get(i).setTranslateX((i - 3) * Config.BoboSizeW);
        }
    }

    public ArrayList<Bobo> take() {
        ArrayList<Bobo> bobos = this.bobos;
        fill();
        return bobos;
    }
}
