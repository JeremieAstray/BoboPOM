package boboPOM.view.main;

import boboPOM.code.basic.Bobo;
import boboPOM.code.basic.Brick;
import boboPOM.code.playerside.Model;
import boboPOM.config.Config;
import boboPOM.config.Level;
import boboPOM.config.Utils;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.LinkedList;

import static boboPOM.code.anime.Transitions.newSt;

/**
 * @author yorlbgy
 */
public class Splash {

    public static final int INITING = -1;
    public static final int DARGING = 0;
    public static final int MOVING = 1;
    public static final int VANISHING = 2;
    public static final int DROPPING = 3;
    public static final int RAISING = 4;
    public static final int SPECIAL = 5;

    private Model model;
    private Timeline timeline;
    private int level;
    private int state;
    private int arg;
    private double sour, target;
    private double speed = 1.5;
    private int timecounter = 0;

    public Splash(Model model, boolean network) {
        this.model = model;
        initTimeline(network);
        level = Level.INIT;
        timeline.setOnFinished(null);
    }

    private void initTimeline(boolean network) {
        timeline = new Timeline();
        timeline.setAutoReverse(false);
        timeline.setCycleCount(Timeline.INDEFINITE);
        arg = 0;
        state = Splash.INITING;
        if (network) {
            state = Splash.SPECIAL;
        }

        KeyFrame kf = new KeyFrame(Duration.millis(40), new EventHandler<ActionEvent>() {
            private LinkedList<Bobo> list;
            private Bobo b;
            private int temp = 0;
            private boolean movingSBurst;
            private boolean animeFinished = true;
            private ArrayList<Bobo> boes;
            private ImageView start;
            private boolean tVanish = true;

            @Override
            public void handle(ActionEvent event) {
                if (state == Splash.INITING) {
                    initing();
                }

                if (state == Splash.DARGING) {
                    darging();
                }
                if (state == Splash.MOVING) {
                    // lineUp
                    timecounter++;
                    lineUp();
                    speedUp();
                    timecounter %= 1000;
                    moving();
                }
                speedUp();
                if (state == Splash.VANISHING) {
//                    // lineUp
//                    timecounter++;
//                    lineUp();
//                    timecounter %= 1800;
                    vanishing();
                }
                if (state == Splash.DROPPING) {
//                    // lineUp
//                    timecounter++;
//                    lineUp();
//                    timecounter %= 1800;
                    dropping();
                }

                if (state == Splash.RAISING) {
                    raising();
                }

                if (state == Splash.SPECIAL) {
                        special();
                }
            }

            private void initing() {
                if (arg == 0) {
                    start = new ImageView(Config.getEffects().get(0));
                    Config.effectMedia.play(12);
                    if (model.getMainModel().getP2().getPersonage() == Config.Campaleila) {
                        Config.bgmMedia.playMusic(1, true);
                    } else {
                        Config.bgmMedia.playMusic(0, true);
                    }
                    model.getPane().getChildren().add(start);
                    for (int i = 0; i < 4; i++) {
                        model.addLine();
                    }
                    arg = 1;
                }
                if (arg == 1) {
                    list = model.getAllBobo(Bobo.STATE_RISING);
                    translateAnimePlay(list, Bobo.STATE_RISING);
                    arg = 2;
                }
                if (arg == 2) {
                    if (animeFinished) {
                        for (Bobo bo : list) {
                            bo.changeState(Bobo.STATE_JUMPING);
                        }
                        state = Splash.VANISHING;
                        arg = 0;
                        model.getPane().getChildren().remove(start);
                    }
                }
            }

            private void darging() {
                if (model.isSBurst()) {
                    model.setSBurst(false);
                }
                if (!this.movingSBurst) {
                    if (!model.setBrick(model.getPrepareSet().takeBrick())) {
                        model.setWin(false);
                        return;
                    }
                } else {
                    this.movingSBurst = false;
                    if (!model.setBrick(model.getBrick())) {
                        model.setWin(false);
                        return;
                    }
                }
                state = Splash.MOVING;
                arg = 0;
            }

            private void moving() {
                if (arg == 0) {
                    arg++;
                }
                if (model.getBrick().isAnimeFinished()) {
                    if (model.isSBurst()) {
                        model.setEnable(false);
                        model.setShadowIn(false);
                        Brick b = model.getBrick();
                        model.getPane().deleteBobo(b.getD().getIndexX(), b.getD().getIndexY());
                        model.getPane().deleteBobo(b.getU().getIndexX(), b.getU().getIndexY());
                        state = Splash.VANISHING;
                        arg = 0;
                        movingSBurst = true;
                        return;
                    } else {
                        model.setEnable(true);
                        model.setShadowIn(true);
                    }
                    if (arg == 1) {
                        sour = model.getBrick().getTranslateY();
                        if (!model.getBrick().moveD()) {
                            model.getBrick().getD().changeState(Bobo.STATE_JUMPING);
                            model.getBrick().getU().changeState(Bobo.STATE_JUMPING);
                            arg = 9;
                        }
                        if (arg != 9) {
                            target = model.getBrick().getTranslateY();
                            //model.getBrick().setTranslateY(sour);
                            arg++;
                        }
                    }
                    if (arg == 2) {
                        sour = sour + speed;
                        model.getBrick().setTranslateY(sour);
                        if (sour >= target) {
                            model.getBrick().setTranslateY(target);
                            arg = 1;
                        }
                    }
                    if (arg == 9) {
                        model.setEnable(false);
                        model.setShadowIn(false);
                        state = Splash.VANISHING;
                        arg = 0;
                    }
                } else {
                    model.setEnable(false);
                }
            }

            private void vanishing() {
                if (arg == 0) {
                    tVanish = true;
                    list = model.getAllBobo(Bobo.STATE_JUMPING);
                    arg = 1;
                }
                if (arg == 1) {
                    ArrayList<Bobo> buff = new ArrayList<>();
                    for (Bobo bo : list) {
                        if (bo.isAnimeFinished()) {
                            buff.add(bo);
                        }
                    }
                    list.removeAll(buff);
                    if (list.isEmpty()) {
                        arg = 2;
                    }
                }

                if (arg == 2) {
                    b = new Bobo(model.getPane());
                    b.setTranslateX(0);
                    boes = findKey(b);
                    model.getCounterSet().spClear(model);
                    if (boes.isEmpty()) {
                        model.getCounterSet().down();
                        state = Splash.RAISING;
                        arg = 0;
                        if (model.isSBurst()) {
                            if (model.isBurstDef()) {
                                //sDef
                                int t = model.getCounterSet().useCP();
                                for (int i = 0; i < t; i++) {
                                    for (int j = 0; j < 7; j++) {
                                        ((Bobo) model.getPane().getChildren().get(Utils.getIndexOfBobo(j, i))).vanish(false);
                                    }
                                }
                                model.setBurstDef(false);
                                state = Splash.VANISHING;
                                arg = 3;
                                tVanish = false;
                            }
                        }

                    } else {
                        arg = 3;
                        int cpu = (int) b.getTranslateX();
                        chains(boes, cpu);
                    }
                }
                if (arg == 3) {
                    list = model.getAllBobo(Bobo.STATE_VANSHING);
                    arg = 4;
                }
                if (arg == 4) {
                    ArrayList<Bobo> buff = new ArrayList<>();
                    for (Bobo bo : list) {
                        if (bo.isAnimeFinished()) {
                            buff.add(bo);
                        }
                    }
                    list.removeAll(buff);
                    if (list.isEmpty()) {
                        arg = 5;
                    }
                }
                if (arg == 5) {
                    delete();
                    arg = 6;
                }
                if (arg == 6) {
                    state = Splash.DROPPING;
                    arg = 0;
                }
            }

            private void dropping() {
                //test
                if (arg == 0) {
                    list = model.getAllBobo(Bobo.STATE_DROPPING);
                    int c = 0;
                    for (Bobo bo : list) {
                        double mt = bo.getTranslateY();
                        while (model.getPane().moveBobo(bo, bo.getDown())) ;
                        bo.setTranslateY(mt);
                    }
                    arg = 1;
                }
                if (arg == 1) {
                    translateAnimePlay(list, Bobo.STATE_RISING);
                    arg = 2;
                }
                if (arg == 2) {
                    if (animeFinished) {
                        for (Bobo bo : list) {
                            bo.changeState(Bobo.STATE_JUMPING);
                        }
                        state = Splash.VANISHING;
                        arg = 0;
                    }
                }
            }

            private void raising() {
                if (arg == 0) {
                    if (model.isSBurst()) {
                        if (model.isBurstAtt()) {
                            Config.effectMedia.play(9);
                            int t = model.getSAtt();
                            model.getCounterSet().addLines(t);
                        }
                        model.setBurstAtt(false);
                    }
                    int ls = model.getCounterSet().useLines();
                    if (ls == Config.LINE_MIN) {
                        model.updataDeep();
                        state = Splash.DARGING;
                        arg = 0;
                    } else {
                        for (int i = 0; i < ls; i++) {
                            model.addLine();
                        }
                        arg = 1;
                    }
                }
                if (arg == 1) {
                    list = model.getAllBobo(Bobo.STATE_RISING);
                    translateAnimePlay(list, Bobo.STATE_RISING);
                    arg = 2;
                }
                if (arg == 2) {
                    if (animeFinished) {
                        for (Bobo bo : list) {
                            bo.changeState(Bobo.STATE_JUMPING);
                        }
                        state = Splash.VANISHING;
                        arg = 0;
                    }
                }
            }

            private ArrayList<Bobo> findKey(Bobo i) {
                LinkedList<Bobo> list = model.getAllBobo(Bobo.STATE_NORMAL);
                ArrayList<Bobo> boes = new ArrayList<Bobo>();
                int sum = 0;
                for (Bobo bi : list) {
                    int c = 0;
                    if (bi.isNull() || bi.getState() != Bobo.STATE_NORMAL || bi.getType() == Bobo.TYPE_WHITE) {
                        continue;
                    }
                    if (bi.getUp() != null && bi.getUp().getType() == bi.getType()) {
                        c++;
                    }
                    if (bi.getDown() != null && bi.getDown().getType() == bi.getType()) {
                        c++;
                    }
                    if (bi.getLeft() != null && bi.getLeft().getType() == bi.getType()) {
                        c++;
                    }
                    if (bi.getRight() != null && bi.getRight().getType() == bi.getType()) {
                        c++;
                    }
                    if (c > 1) {
                        sum += Integer.valueOf(bi.vanish(true));
                        boes.add(bi);
                    }

                }
                i.setTranslateX(sum);
                return boes;
            }

            private void lineUp() {

                if (Utils.lineUp(timecounter, model.getDeep())) {
                    model.getCounterSet().addLines(1);
                    timecounter = 0;
                }
            }

            private synchronized void speedUp() {
                if (temp < 4000)
                    temp += 1;

                if (temp == 1000) {
                    speed = 3;
                }
                if (temp == 2000) {
                    speed = 5;
                }
                if (temp == 3000) {
                    speed = 7;

                }
                if (temp == 4000) {
                    speed = 10;
                    temp++;
                }
            }

            private void chains(ArrayList<Bobo> boes, int cpu) {
                boolean newChain = true;
                for (Bobo bo : boes) {
                    model.getCounterSet().addCP(cpu);
                    model.getMainFrame().getChildren().add(model.getCounterSet().chains(bo.getTranslateX() + bo.getPlayerSide().getTranslateX(), bo.getTranslateY() + bo.getPlayerSide().getTranslateY(), newChain));
                    if (newChain) {
                        newChain = false;
                    }
                }
            }

            private void delete() {
                LinkedList<Bobo> bos = model.getAllBobo(Bobo.STATE_VANSHING);
                for (Bobo bi : bos) {
                    if (bi.getUp().getState() == Bobo.STATE_NORMAL) {
                        bi.getUp().changeState(Bobo.STATE_DROPPING);
                    }
                    if (tVanish == true) {
                        if (bi.getType() == Bobo.TYPE_WHITE) {
                            ghostAnimePlay(bi);
                        }
                    }
                    model.deleteBobo(bi);
                }
            }

            private void translateAnimePlay(LinkedList<Bobo> list, int state) {
                ParallelTransition pt = new ParallelTransition();
                for (int i = 0; i < list.size(); i++) {
                    TranslateTransition tt = new TranslateTransition(Duration.millis(200), list.get(i));
                    tt.setToY(list.get(0).getPlayerSide().getPosY(list.get(i).getIndexY()));
                    tt.setAutoReverse(false);
                    tt.setCycleCount(1);
                    pt.getChildren().add(tt);
                }
                pt.setAutoReverse(false);
                pt.setCycleCount(1);
                pt.setOnFinished(new EventHandler() {

                    @Override
                    public void handle(Event arg0) {
                        animeFinished = true;
                    }

                });
                animeFinished = false;
                pt.play();
            }

            private void ghostAnimePlay(Bobo bo) {
                ArrayList<ImageView> list = new ArrayList<>();
                Image im = Config.getEffects().get(8);
                for (int i = 0; i < 10; i++) {
                    ImageView iv = new ImageView(im);
                    iv.setStyle("-fx-opacity: 0.5;");
                    iv.setTranslateX(bo.getTranslateX() + bo.getPlayerSide().getTranslateX());
                    iv.setTranslateY(bo.getTranslateY() + bo.getPlayerSide().getTranslateY());
                    iv.setScaleX(iv.getScaleX() - i * 0.1);
                    iv.setScaleY(iv.getScaleY() - i * 0.1);
                    list.add(iv);
                }
                model.getMainFrame().getChildren().addAll(list);
                ParallelTransition pt = new ParallelTransition();
                for (int i = 0; i < list.size(); i++) {
                    TranslateTransition tt = new TranslateTransition(Duration.millis(500), list.get(i));
                    if (model.isP1()) {
                        tt.setToX(-30);
                        tt.setToY(-90);
                    } else {
                        tt.setToX(30);
                        tt.setToY(90);
                    }
                    tt.setAutoReverse(false);
                    tt.setCycleCount(1);
                    tt.setDelay(Duration.millis(60 * i));
                    tt.setInterpolator(Interpolator.EASE_IN);
                    pt.getChildren().add(tt);
                }
                pt.setAutoReverse(false);
                pt.setCycleCount(1);
                pt.setOnFinished(new EventHandler() {

                    @Override
                    public void handle(Event arg0) {
                        ScaleTransition newSt = newSt(Duration.millis(500), 2, false);
                        ImageView vanishEffect = new ImageView(Config.getEffects().get(16));
                        vanishEffect.setStyle("-fx-opacity: 0.5;");
                        vanishEffect.setTranslateX(list.get(0).getTranslateX());
                        vanishEffect.setTranslateY(list.get(0).getTranslateY());
                        newSt.setNode(vanishEffect);
                        model.getMainFrame().getChildren().add(vanishEffect);
                        newSt.setCycleCount(1);
                        newSt.setOnFinished(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent arg0) {
                                model.getMainFrame().getChildren().remove(vanishEffect);
                            }

                        });
                        newSt.play();
                        model.getMainFrame().getChildren().removeAll(list);
                    }

                });
                pt.play();
            }

            private void special() {
                if (arg == 0) {
                    if (model.isP1()) {
                        model.getMainModel().getP2().send(true);
                    } else {
                        model.getMainModel().getP1().send(true);
                    }
                    arg++;
                }
                if (arg == 1) {
//                    if(timecounter == 0){
                    if (model.isP1()) {
                        model.getMainModel().getP2().send(false);
                    } else {
                        model.getMainModel().getP1().send(false);
                    }
                }
//                    timecounter++;
//                    timecounter %= 1;
//                }
            }
        });

        timeline.getKeyFrames().add(kf);
    }

    public void play() {
        timeline.play();
    }

    public int getState() {
        return state;
    }

    public Timeline getTimeline() {
        return this.timeline;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return this.speed;
    }
}
