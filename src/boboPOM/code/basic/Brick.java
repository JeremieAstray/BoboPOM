package boboPOM.code.basic;

import boboPOM.config.Config;
import boboPOM.code.main.MainFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.util.Duration;

import static boboPOM.code.anime.Transitions.newTt;


/**
 * @author yorlbgy
 */
public class Brick {

    private Bobo u, d;
    private boolean AnimeFinished = true;

    public Brick(Bobo u, Bobo d) {
        this.u = u;
        this.d = d;
        u.setTranslateY(d.getTranslateY() - Config.BoboSizeH);
    }

    public synchronized void swap() {
        Config.effectMedia.play(7);
        animePlay();
        double dy = d.getTranslateY();
        double uy = u.getTranslateY();
        Bobo t = u;
        t.getPlayerSide().deleteBobo(u.getIndexX(), u.getIndexY());
        t.getPlayerSide().moveBobo(d, u);
        t.getPlayerSide().addBobo(t, d.getIndexX(), d.getIndexY() - 1);
        u = d;
        d = t;
        d.setTranslateY(uy);
        u.setTranslateY(dy);
    }

    public Bobo getU() {
        return u;
    }

    public Bobo getD() {
        return d;
    }

    public void add(MainFrame p) {
        p.getChildren().addAll(u, d);
    }

    public Bobo[] getUD() {
        Bobo[] bo = new Bobo[2];
        bo[0] = u;
        bo[1] = d;
        return bo;
    }

    public boolean isAnimeFinished() {
        return AnimeFinished;
    }

    public final double getTranslateX() {
        return d.getTranslateX();
    }

    public final void setTranslateX(double arg0) {
        d.setTranslateX(arg0);
        u.setTranslateX(arg0);
    }

    public final double getTranslateY() {
        return d.getTranslateY();
    }

    public final void setTranslateY(double arg0) {
        d.setTranslateY(arg0);
        u.setTranslateY(arg0 - Config.BoboSizeH);
    }

    public final boolean moveL() {
        synchronized (this) {
            double dy = d.getTranslateY();
            double uy = u.getTranslateY();
            boolean b = d.getPlayerSide().moveBobo(d, d.getLeft());
            if (!b) {
                return b;
            }
            u.getPlayerSide().moveBobo(u, u.getLeft());
            d.setTranslateY(dy);
            u.setTranslateY(uy);
            return true;
        }
    }

    public final boolean moveR() {
        synchronized (this) {
            double dy = d.getTranslateY();
            double uy = u.getTranslateY();
            boolean b = d.getPlayerSide().moveBobo(d, d.getRight());
            if (!b) {
                return b;
            }
            u.getPlayerSide().moveBobo(u, u.getRight());
            d.setTranslateY(dy);
            u.setTranslateY(uy);
            return true;
        }
    }

    public final boolean moveD() {
        synchronized (this) {
            boolean b = d.getPlayerSide().moveBobo(d, d.getDown());
            if (!b) {
                return b;
            }
            u.getPlayerSide().moveBobo(u, u.getDown());
            return true;
        }
    }


    private void animePlay() {
        ParallelTransition pt = new ParallelTransition();
        pt.getChildren().add(newTt(Duration.millis(200), 0, -Config.BoboSizeH));
        pt.getChildren().add(newTt(Duration.millis(200), 0, Config.BoboSizeH));
        ((TranslateTransition) pt.getChildren().get(0)).setNode(d);
        ((TranslateTransition) pt.getChildren().get(1)).setNode(u);
        pt.setOnFinished(new EventHandler() {

            @Override
            public void handle(Event arg0) {
                AnimeFinished = true;
            }

        });
        this.AnimeFinished = false;
        pt.play();
    }

}
