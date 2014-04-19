
package boboPOM.code.anime;

import boboPOM.config.Config;
import boboPOM.code.basic.Bobo;
import boboPOM.code.basic.Brick;
import boboPOM.code.prepareContainer.QpPane;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 *
 * @author yorlbgy
 */
public class Transitions {

    private static ArrayList<ParallelTransition> transitions;
    private static ArrayList<ArrayList<Bobo>> targetList;

    public static void initTransitions() {
        transitions = new ArrayList<>();
        targetList = new ArrayList<>(16);
        for (int i = 0; i < 16; i++) {
            targetList.add(new ArrayList<>());
        }
        ParallelTransition pt;

        //Animes.BOBO_INIT_UP
        pt = new ParallelTransition();
        for (int i = 0; i < 4 * 7; i++) {
            pt.getChildren().add(newTt(Duration.millis(3000), 0, -Config.BoboSizeH * 4));
        }
        transitions.add(pt);

        //Animes.BRICK_DROP
        pt = new ParallelTransition();
        for (int i = 0; i < 2; i++) {
            pt.getChildren().add(newTt(Duration.millis(900), 0, Config.BoboSizeH));
        }
        transitions.add(pt);

        //Animes.BOBO_UP
        pt = new ParallelTransition();
        pt.getChildren().add(newTt(Duration.millis(200), 0, -Config.BoboSizeH));
        transitions.add(pt);

        //Animes.BOBO_DROP
        pt = new ParallelTransition();
        pt.getChildren().add(newTt(Duration.millis(300), 0, Config.BoboSizeH));
        transitions.add(pt);

        // Animes.BOBO_SHINNING
        pt = new ParallelTransition();
        pt.getChildren().add(newSt(Duration.millis(800), 3, true));
        transitions.add(pt);

        //Animes.CP_SHINNING
        pt = new ParallelTransition();
        ScaleTransition st;
        for (int i = 0; i < 3; i++) {
            st = newSt(Duration.millis(800), 3, true);
            st.setDelay(Duration.millis((i + 1) * 300));
            pt.getChildren().add(st);
        }
        transitions.add(pt);

        //Animes.BRICK_SWAP
     
        transitions.add(pt);

        //BOBO_BURST
        pt = new ParallelTransition();
        pt.getChildren().add(newSt(Duration.millis(500), 3, false));
        transitions.add(pt);
    }

    public static TranslateTransition newTt(Duration duration, double distanceX, double distanceY) {
        TranslateTransition tt;
        tt = new TranslateTransition(duration);
        tt.setAutoReverse(false);
        tt.setCycleCount(1);
        tt.setByX(distanceX);
        tt.setByY(distanceY);
//        tt.setInterpolator(Interpolator.LINEAR);
        return tt;
    }

    public static ScaleTransition newSt(Duration duration, double scale, boolean autoReverse) {
        ScaleTransition st;
        st = new ScaleTransition(duration);
        st.setCycleCount(Transition.INDEFINITE);
        st.setAutoReverse(autoReverse);
        st.setFromX(0.7);
        st.setFromY(0.7);
        st.setByX(scale);
        st.setByY(scale);
        return st;
    }

    public static void playBrick_Drop(Brick b) {
        ParallelTransition pt = (ParallelTransition) Transitions.transitions.get(Animes.BRICK_DROP);
        System.out.println( b == null);
        System.out.println(pt == null);
        ((TranslateTransition) pt.getChildren().get(0)).setNode(b.getD());
        ((TranslateTransition) pt.getChildren().get(0)).setNode(b.getU());
        pt.play();
    }

    private static void playBobo_Up(ArrayList<Bobo> bobos) {
        ParallelTransition pt = (ParallelTransition) Transitions.transitions.get(Animes.BOBO_UP);
        pt.pause();
        if (pt.getChildren().size() < bobos.size()) {
            for (int i = pt.getChildren().size(); i < bobos.size(); i++) {
                pt.getChildren().add(newTt(Duration.millis(200), 0, -Config.BoboSizeH));
            }
        }
        if (pt.getChildren().size() > bobos.size()) {
            pt.getChildren().remove(bobos.size(), pt.getChildren().size() - 1);
        }
        for (int i = 0; i < bobos.size(); i++) {
            ((TranslateTransition) pt.getChildren().get(i)).setNode(bobos.get(i));
        }
        pt.play();
    }

    public static void setPlayBobo_Drop(ArrayList<Bobo> bobos) {
        Transitions.targetList.set(Animes.BOBO_DROP, bobos);
        playBobo_Drop(targetList.get(Animes.BOBO_DROP));
    }

    public static void removePlayBobo_Drop(ArrayList<Bobo> bobos) {
        targetList.get(Animes.BOBO_DROP).removeAll(bobos);
        playBobo_Drop(targetList.get(Animes.BOBO_DROP));
    }

    private static void playBobo_Drop(ArrayList<Bobo> bobos) {
        ParallelTransition pt = (ParallelTransition) Transitions.transitions.get(Animes.BOBO_DROP);
        pt.pause();
        if (pt.getChildren().size() < bobos.size()) {
            for (int i = pt.getChildren().size(); i < bobos.size(); i++) {
                pt.getChildren().add(newTt(Duration.millis(300), 0, Config.BoboSizeH));
            }
        }
        if (pt.getChildren().size() > bobos.size()) {
            pt.getChildren().remove(bobos.size(), pt.getChildren().size() - 1);
        }
        for (int i = 0; i < bobos.size(); i++) {
            ((TranslateTransition) pt.getChildren().get(i)).setNode(bobos.get(i));
        }
        pt.play();
    }

    public static void setPlayBobo_Shinning(ArrayList<Bobo> bobos) {
        Transitions.targetList.set(Animes.BOBO_SHINNING, bobos);
        PlayBobo_Shinning(targetList.get(Animes.BOBO_SHINNING));
    }

    public static void addPlayBobo_Shinning(ArrayList<Bobo> bobos) {
        targetList.get(Animes.BOBO_SHINNING).addAll(bobos);
        PlayBobo_Shinning(targetList.get(Animes.BOBO_SHINNING));
    }

    public static void addPlayBobo_Shinning(Bobo bo) {
        if (!targetList.get(Animes.BOBO_SHINNING).contains(bo)) {
            targetList.get(Animes.BOBO_SHINNING).add(bo);
        }
        PlayBobo_Shinning(targetList.get(Animes.BOBO_SHINNING));
    }

    public static void removePlayBobo_Shinning(ArrayList<Bobo> bobos) {
        targetList.get(Animes.BOBO_SHINNING).removeAll(bobos);
        PlayBobo_Shinning(targetList.get(Animes.BOBO_SHINNING));
    }

    public static void removePlayBobo_Shinning(Bobo bo) {
        if (!targetList.get(Animes.BOBO_SHINNING).contains(bo)) {
            targetList.get(Animes.BOBO_SHINNING).remove(bo);
        }
        PlayBobo_Shinning(targetList.get(Animes.BOBO_SHINNING));
    }

    private static void PlayBobo_Shinning(ArrayList<Bobo> bobos) {
        ParallelTransition pt = new ParallelTransition();
        if (pt.getChildren().size() < bobos.size()) {
            for (int i = pt.getChildren().size(); i < bobos.size(); i++) {
                pt.getChildren().add(newSt(Duration.millis(800), 3, true));
            }
        }
        if (pt.getChildren().size() > bobos.size()) {
            pt.getChildren().remove(bobos.size(), pt.getChildren().size() - 1);
        }
        for (int i = 0; i < bobos.size(); i++) {
            ImageView iv = new ImageView(Config.getEffects().get(9));
            iv.setStyle("-fx-opacity:0.5;");
            iv.setTranslateX(bobos.get(i).getTranslateX());
            iv.setTranslateY(bobos.get(i).getTranslateY());
            ((ScaleTransition) pt.getChildren().get(i)).setNode(iv);
            bobos.get(i).getPlayerSide().getChildren().add(iv);
        }
        pt.play();
    }

    public static ImageView PlayCP_Shinning(Bobo bo) {
        ParallelTransition pt = (ParallelTransition) Transitions.transitions.get(Animes.CP_SHINNING);
        pt.pause();
        ImageView iv = new ImageView(Config.getEffects().get(13));
        iv.setStyle("-fx-opacity:0.5;");
        iv.setTranslateX(bo.getTranslateX());
        iv.setTranslateY(bo.getTranslateY());
        ((ScaleTransition) pt.getChildren().get(0)).setNode(iv);
        return iv;
    }
    
   public static void PlayBrick_Up(QpPane qp){
       
   }

    public static ArrayList<ImageView> setPlayBobo_Burst(ArrayList<Bobo> bobos) {
        Transitions.targetList.set(Animes.BOBO_BURST, bobos);
        return PlayBobo_Burst(targetList.get(Animes.BOBO_BURST));
    }

    public static ArrayList<ImageView> addPlayBobo_Burst(ArrayList<Bobo> bobos) {
        targetList.get(Animes.BOBO_BURST).addAll(bobos);
        return PlayBobo_Burst(targetList.get(Animes.BOBO_BURST));
    }

    private static ArrayList<ImageView> PlayBobo_Burst(ArrayList<Bobo> bobos) {
        ParallelTransition pt = (ParallelTransition) Transitions.transitions.get(Animes.BOBO_BURST);
        if (pt.getChildren().size() < bobos.size()) {
            for (int i = pt.getChildren().size(); i < bobos.size(); i++) {
                pt.getChildren().add(newSt(Duration.millis(500), 3, false));
            }
        }
        if (pt.getChildren().size() > bobos.size()) {
            pt.getChildren().remove(bobos.size(), pt.getChildren().size() - 1);
        }
        ArrayList<ImageView> viewList = new ArrayList<>();
        for (int i = 0; i < bobos.size(); i++) {
            ImageView iv = new ImageView(Config.getEffects().get(bobos.get(i).getType() + 12));
            iv.setStyle("-fx-opacity:0.5;");
            iv.setTranslateX(bobos.get(i).getPlayerSide().getTranslateX() + bobos.get(i).getTranslateX());
            iv.setTranslateY(bobos.get(i).getPlayerSide().getTranslateY() + bobos.get(i).getTranslateY());
            ((ScaleTransition) pt.getChildren().get(i)).setNode(iv);
            viewList.add(iv);
        }
        pt.play();
        return viewList;
    }
}
