
package boboPOM.code.basic;

import boboPOM.config.Config;
import boboPOM.code.playerside.PlayerSide;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import static boboPOM.code.anime.Transitions.newSt;

/**
 *
 * @author yorlbgy
 */
public class Bobo extends ImageView {

    public static final int NULL = -1;
    public static final int TYPE_YELLOW = 0;
    public static final int TYPE_RED = 1;
    public static final int TYPE_BLUE = 2;
    public static final int TYPE_GREEN = 3;
    public static final int TYPE_WHITE = 4;

    public static final int STATE_BRICK = 0;
    public static final int STATE_JUMPING = 1;
    public static final int STATE_NORMAL = 2;
    public static final int STATE_VANSHING = 3;
    public static final int STATE_DROPPING = 4;
    public static final int STATE_RISING = 5;
    private int type;
    private int view;
    private int state;
    private PlayerSide parent;
    private int x;
    private int y;
    private Timeline tl;
    private ImageView vanishEffect;
    private ImageView shinningEffect;
    private boolean animeFinished = true;

    public Bobo(int type, int state) {
        changeType(type);
        this.state = state;
        initTimeline();
    }

    public Bobo(PlayerSide parent) {
        this(parent, NULL, STATE_NORMAL);
    }

    public Bobo(PlayerSide parent, int type, int state) {
        this.parent = parent;
        this.view = 0;
        changeType(type);
        this.state = state;
        initTimeline();
    }

    public Bobo(PlayerSide parent, boolean up) {
        this(parent);
        this.x = 0;
        this.y = 16;
    }

    private void changeType(int newType) {
        this.type = newType;
        Image image;
        if (type == NULL) {
            image = Config.getBobosImages().get(type + 1).get(this.view);
        } else {
            image = Config.getBobosImages().get(type).get(this.view);
            this.setImage(image);
            this.vanishEffect = new ImageView(Config.getEffects().get(type + 12));
            if (type == Bobo.TYPE_WHITE) {
                this.shinningEffect = new ImageView(Config.getEffects().get(9));
                   shinningEffect.setStyle("-fx-opacity:0.5;");
            }
        }
    }

    public int getType() {
        return type;
    }

    public void changeState(int newState) {
        if (newState == Bobo.STATE_JUMPING) {
            if ((state == Bobo.STATE_BRICK || state == Bobo.STATE_DROPPING)) {
                this.state = newState;
                Config.effectMedia.play(7);
                stateAnimePlay(Bobo.STATE_DROPPING);
                return;
            }
            if (state == Bobo.STATE_RISING) {
                this.state = newState;
                Config.effectMedia.play(7);
                stateAnimePlay(Bobo.STATE_RISING);
                return;
            }
        }
        if (newState == Bobo.STATE_DROPPING) {
            if (this.isNull()) {
                return;
            }
            this.state = newState;
            if (this.getUp().getState() == Bobo.STATE_NORMAL) {
                this.getUp().changeState(Bobo.STATE_DROPPING);
            }
        }

        if (state == Bobo.STATE_NORMAL&&newState == Bobo.STATE_VANSHING) {
            this.state = newState;
            this.setView(7);
            stateAnimePlay(Bobo.STATE_VANSHING);       
        }
        
        if(newState == Bobo.STATE_RISING){
            this.state = newState;
        }
        
        if(newState == Bobo.STATE_NORMAL){
            this.state = newState;
        }
        
        if (this.type == Bobo.TYPE_WHITE) {
            if (newState == Bobo.STATE_NORMAL) {
                shinningAnimePlay();
            } else {
                this.getPlayerSide().getChildren().remove(this.shinningEffect);
            }
        }

    }

    public int getState() {
        return state;
    }

    public void setView(int view) {
        this.view = view;
        if (!this.isNull()) {
            this.setImage(Config.getBobosImages().get(type).get(this.view));
        }
    }

    public Bobo getUp() {
        Node node = parent.getBobo(x, y + 1);
        if (node != null) {
            return (Bobo) node;
        } else {
            return new Bobo(parent, true);
        }
    }

    public Bobo getDown() {
        Node node = parent.getBobo(x, y - 1);
        if (node != null) {
            return (Bobo) node;
        } else {
            return null;
        }
    }

    public Bobo getLeft() {
        Node node = parent.getBobo(x - 1, y);
        if (node != null) {
            return (Bobo) node;
        } else {
            return null;
        }
    }

    public Bobo getRight() {
        Node node = parent.getBobo(x + 1, y);
        if (node != null) {
            return (Bobo) node;
        } else {
            return null;
        }
    }

    public void setIndexX(int x) {
        this.x = x;
        setTranslateX(parent.getPosX(x));
        if (this.getType() == Bobo.TYPE_WHITE) {
            this.shinningEffect.setTranslateX(getTranslateX());
        }
    }

    public void setIndexY(int y) {
        this.y = y;
        setTranslateY(parent.getPosY(y));
        if (this.getType() == Bobo.TYPE_WHITE) {
            this.shinningEffect.setTranslateY(getTranslateY());
        }
    }

    public int getIndexX() {
        return this.x;
    }

    public int getIndexY() {
        return this.y;
    }

    public boolean isNull() {
        return this.getType() == NULL;
    }

    public PlayerSide getPlayerSide() {
        return parent;
    }

    public void setPlayerSide(PlayerSide aThis) {
        this.parent = aThis;
    }

    public int vanish(Boolean s) {
        if (this.isNull()) {
            return 0;
        }
        if (!s) {
            changeState(Bobo.STATE_VANSHING);
            return 0;
        } else {
            int c = 0;
            this.changeState(Bobo.STATE_VANSHING);
            if (this.getType() != Bobo.TYPE_WHITE) {           
                if (this.getUp() != null && (this.getUp().getType() == this.getType() || this.getUp().getType() == Bobo.TYPE_WHITE) && this.getUp().getState() == Bobo.STATE_NORMAL) {
                    c += this.getUp().vanish(true);
                }
                if (this.getDown() != null && (this.getDown().getType() == this.getType() || this.getDown().getType() == Bobo.TYPE_WHITE) && this.getDown().getState() == Bobo.STATE_NORMAL) {
                    c += this.getDown().vanish(true);
                }
                if (this.getLeft() != null && (this.getLeft().getType() == this.getType() || this.getLeft().getType() == Bobo.TYPE_WHITE) && this.getLeft().getState() == Bobo.STATE_NORMAL) {
                    c += this.getLeft().vanish(true);
                }
                if (this.getRight() != null && (this.getRight().getType() == this.getType() || this.getRight().getType() == Bobo.TYPE_WHITE) && this.getRight().getState() == Bobo.STATE_NORMAL) {
                    c += this.getRight().vanish(true);
                }
            } else {
                c += 74;
            }
            return c + 1;
        }
    }

    private void stateAnimePlay(int state) {
        synchronized (this) {
            initTimeline();
            KeyFrame kf;
            Bobo bo = this;
            if (state == Bobo.STATE_DROPPING) {
                kf = new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                        bo.setView(view);
                        view++;
                        if (view > 6) {
                            view = 0;
                        }
                    }
                });
                tl.getKeyFrames().clear();
                tl.getKeyFrames().add(kf);
                tl.setAutoReverse(false);
                tl.setCycleCount(7);
                tl.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                        bo.changeState(Bobo.STATE_NORMAL);
                        animeFinished = true;
                    }
                });
                tl.play();
            }

            if (state == Bobo.STATE_RISING) {
                kf = new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                        bo.setView(view);
                        if (view == 0) {
                            view = 5;
                            return;
                        }
                        if (view == 5) {
                            view = 4;
                            return;
                        }
                        if (view == 4) {
                            view = 0;
                            return;
                        }
                    }
                });
                tl.getKeyFrames().clear();
                tl.getKeyFrames().add(kf);
                tl.setAutoReverse(false);
                tl.setCycleCount(4);
                tl.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                        bo.changeState(Bobo.STATE_NORMAL);
                        view = 0;
                        animeFinished = true;
                    }
                });
                tl.play();
            }

            if (state == Bobo.STATE_VANSHING) {
                 Config.effectMedia.play(8);
                ScaleTransition newSt = newSt(Duration.millis(500), 1.2, false);
                vanishEffect.setTranslateX(bo.getTranslateX());
                vanishEffect.setTranslateY(bo.getTranslateY());
                newSt.setNode(vanishEffect);
                bo.parent.getChildren().add(vanishEffect);
                newSt.setCycleCount(1);
                newSt.setOnFinished(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent arg0) {
                        bo.parent.getChildren().remove(vanishEffect);
                        animeFinished = true;
                    }

                });
                newSt.play();
            }
            this.animeFinished = false;
        }
    }

    private void shinningAnimePlay() {
        ParallelTransition pt = new ParallelTransition();
        shinningEffect.setTranslateX(this.getTranslateX());
        shinningEffect.setTranslateY(this.getTranslateY());
        pt.getChildren().add(newSt(Duration.millis(800), 1.2, true));
        ((ScaleTransition) pt.getChildren().get(0)).setNode(shinningEffect);
        this.getPlayerSide().getChildren().add(shinningEffect);
        pt.play();
    }

    private void initTimeline() {
        tl = new Timeline();
        tl.setAutoReverse(false);
        tl.setCycleCount(1);
    }

    public boolean isAnimeFinished(){
        return animeFinished;
    }



}
