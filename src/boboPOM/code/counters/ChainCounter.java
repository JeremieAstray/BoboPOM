package boboPOM.code.counters;

import boboPOM.config.Config;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

/**
 * @author yorlbgy
 */
public class ChainCounter extends Parent {

    private boolean p1;
    private int chains;
    private ContentPane content;
    private CPCounter cc;

    public ChainCounter(boolean p1) {
        this.p1 = p1;
        chains = 0;
        content = new ContentPane();
    }

    public StackPane chains(double x, double y) {
        if (chains++ == 0) {
            return new StackPane();
        }
        int bouns = getBouns();
        cc.addCP(bouns);
        return content.turnUp(chains, bouns, x, y);
    }

    public void down() {
        chains = 0;
    }

    void setCPCounter(CPCounter cc) {
        this.cc = cc;
    }

    private int getBouns() {
        switch (chains) {
            case 0:
                return 0;
            case 1:
                return 0;
            case 2:
                return 25;
            case 3:
                return 45;
            case 4:
                return 75;
            default:
                return 75;
        }
    }

    private class ContentPane {

        private ArrayList<Image> numbers;
        private Image mod;

        public ContentPane() {
            mod = Config.getEffects().get(3);
            numbers = Config.getNumbers().get(2);
        }

        public void setNumbers(StackPane pane, int number, boolean chains) {
            int t = number % 100 / 10;
            int c = number % 10;
            ImageView ivt = new ImageView(numbers.get(t));
            ivt.setVisible(t > 0);
            ImageView ivc = new ImageView(numbers.get(c));
            if (chains) {
                ivc.setTranslateX(-ivc.getImage().getWidth() / 2 - mod.getWidth() / 2);
                ivc.setTranslateY(-mod.getHeight() / 4);
                ivt.setTranslateX(ivc.getTranslateX() - ivt.getImage().getWidth());
                ivt.setTranslateY(-mod.getHeight() / 4);
            } else {
                ivc.setTranslateX(ivc.getImage().getWidth() / 2);
                ivc.setTranslateY(mod.getHeight() / 4);
                ivt.setTranslateX(ivc.getTranslateX() - ivt.getImage().getWidth());
                ivt.setTranslateY(mod.getHeight() / 4);
            }
            pane.getChildren().addAll(ivt, ivc);
        }

        public StackPane turnUp(int chains, int bouns, double posX, double posY) {
            StackPane sp = new StackPane();
            sp.getChildren().add(new ImageView(mod));
            setNumbers(sp, chains, true);
            setNumbers(sp, bouns, false);
            sp.setTranslateX(posX);
            sp.setTranslateY(posY);
            return sp;
        }
    }
}
