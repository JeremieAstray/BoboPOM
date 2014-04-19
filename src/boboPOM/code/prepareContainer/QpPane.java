
package boboPOM.code.prepareContainer;

import boboPOM.code.basic.Brick;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import boboPOM.config.Config;
import static boboPOM.code.anime.Transitions.newTt;

/**
 *
 * @author yorlbgy
 */
public class QpPane extends StackPane {

    private boolean p1;
    private QueueList<Brick> source, list;
    private double interval = Config.BoboSizeH * 2.1;
    private ParallelTransition pt;
   private boolean AnimeFinished = true; 
    public QpPane(boolean p1) {
        this.p1 = p1;
        list = new QueueList<>();
        if (this.p1) {
            source = BoboFactory.qp1;
            //test
            this.setTranslateX(-440);
        } else {
            source = BoboFactory.qp2;
            this.setTranslateX(440);
        }
        //this.setPrefSize(USE_PREF_SIZE, USE_PREF_SIZE);
        firstFill();
    }

    private void firstFill() {
        Brick b;
        double v;
        for (int i = 0; i < 5; i++) {
            v = (i - 2) * (interval) + Config.BoboSizeH * 0.5;
            b = source.take();
            b.setTranslateY(v);
            this.getChildren().addAll(b.getUD());
            list.add(b);
        }
    }

    public Brick take() {
        Brick brick = list.take();
        this.getChildren().removeAll(brick.getU(), brick.getD());
        fill();
        return brick;
    }

    private void fill() {
        Brick b = source.take();
        if (source.size() <= 6) {
            BoboFactory.makeBrick();
        }
        if (p1) {
            b.setTranslateX(-interval);
        } else {
            b.setTranslateX(interval);
        }
        b.setTranslateY(interval * 2 + Config.BoboSizeH * 0.5);
        list.add(b);
        this.getChildren().addAll(b.getUD());
        animePlay();
    }

     public boolean isAnimeFinished() {
        return AnimeFinished;
    }
      
    private void animePlay() {
         initPt();
        for (int i = 0; i < pt.getChildren().size(); i++) {
            if (i % 2 == 0) {
                ((TranslateTransition) pt.getChildren().get(i)).setNode(list.get(i / 2).getD());
            } else {
                ((TranslateTransition) pt.getChildren().get(i)).setNode(list.get(i / 2).getU());
            }
        }
        AnimeFinished = false;
        pt.play();
    }

    private void initPt() {
        pt = new ParallelTransition();
        Duration time = Duration.millis(600);
        for (int i = 0; i < 8; i++) {
            pt.getChildren().add(newTt(time, 0, -interval));
        }
        if (p1) {
            pt.getChildren().add(newTt(time, interval, 0));
            pt.getChildren().add(newTt(time, interval, 0));
        } else {
            pt.getChildren().add(newTt(time, -interval, 0));
            pt.getChildren().add(newTt(time, -interval, 0));
        }
        pt.setAutoReverse(false);
        pt.setCycleCount(1);
        pt.setOnFinished(new EventHandler(){

            @Override
            public void handle(Event arg0) {
              AnimeFinished = true;
            }
            
        });
    }
}
