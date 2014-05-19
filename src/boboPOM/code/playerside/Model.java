package boboPOM.code.playerside;

import boboPOM.code.basic.Bobo;
import boboPOM.code.basic.Brick;
import boboPOM.code.counters.CounterSet;
import boboPOM.code.prepareContainer.PrepareSet;
import boboPOM.config.Config;
import boboPOM.controller.OpEvent;
import boboPOM.net.encoding.FirstMessage;
import boboPOM.net.encoding.UpdataMessage;
import boboPOM.view.main.MainFrame;
import boboPOM.view.main.MainModel;
import boboPOM.view.main.Splash;
import boboPOM.view.main.UpdataEvent;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author yorlbgy
 */
public class Model implements EventHandler<OpEvent> {

    private final boolean p1;
    private int deep;
    private Brick brick;
    private int state;
    private int personage;
    private final CounterSet counterSet;
    private final PrepareSet prepareSet;
    private final PlayerSide pane;
    private boolean enable = false;
    private boolean sBurst = false;
    private boolean burstAtt = false;
    private boolean burstDef = false;
    private MainFrame mf;
    private Splash sp;
    private boolean sDown = false;
    private boolean swapDown = false;
    private int SAtt;
    public MainModel mm;
    private double speedTemp = 1.5;
    //network use
    private int sattCPToRival = 0;
    private boolean win = true;
    private boolean network;
    //network use

    public Model(boolean p1, boolean network) {
        this.p1 = p1;
        counterSet = new CounterSet(p1);
        prepareSet = new PrepareSet(p1);
        personage = Config.Robozy;
        deep = 0;
        pane = new PlayerSide(p1, personage);
        sp = new Splash(this, network);
        this.network = network;
    }

    public boolean isP1() {
        return p1;
    }

    public void setMainFrame(MainFrame mf) {
        this.mf = mf;
    }

    public MainFrame getMainFrame() {
        return mf;
    }

    public void setMainModel(MainModel mm) {
        this.mm = mm;
    }

    public MainModel getMainModel() {
        return mm;
    }

    public Splash getSp() {
        return sp;
    }

    public PlayerSide getPane() {
        return pane;
    }

    public int getPersonage() {
        return personage;
    }

    public void setPersonage(int personage) {
        this.personage = personage;
        pane.setPersonage(personage);
    }

    public CounterSet getCounterSet() {
        return counterSet;
    }

    public PrepareSet getPrepareSet() {
        return prepareSet;
    }

    public double getPosX(int x) {
        return pane.getPosX(x);
    }

    public double getPosY(int y) {
        return pane.getPosY(y);
    }

    public Bobo getBobo(int x, int y) {
        return pane.getBobo(x, y);
    }

    public void deleteBobo(Bobo bo) {
        pane.deleteBobo(bo.getIndexX(), bo.getIndexY());
    }

    public boolean addBobo(Bobo bobo, int x, int y) {
        return pane.addBobo(bobo, x, y);
    }

    public boolean moveBobo(int x, int y, int newX, int newY) {
        return pane.moveBobo(x, y, newX, newY);
    }

    public void updataDeep() {
        deep = this.pane.getDeep();
        this.pane.getShadow().setWarning(deep >= 8);
    }

    public int getDeep() {
        return deep;
    }

    public void regSet(Pane pane) {
        counterSet.regCounters(pane);
        prepareSet.regPrepareSet(pane);
    }

    public Brick getBrick() {
        return brick;
    }

    public boolean setBrick(Brick brick) {
        if (!pane.getBobo(3, 12).isNull()) {
            return false;
        }
        this.brick = brick;
        pane.addBobo(brick.getD(), 3, 12);
        pane.addBobo(brick.getU(), 3, 13);
        this.pane.getShadow().setIndex(3);
        return true;
    }

    public void takeBrick() {
        this.brick = this.prepareSet.takeBrick();
    }

    public void setEnable(boolean enable) {
        this.enable = enable;

    }

    public void setShadowIn(boolean enable) {
        if (!enable) {
            this.pane.getShadow().out();
        } else {
            this.pane.getShadow().setIndex(this.brick.getD().getIndexX());
        }
    }

    public boolean isSBurst() {
        return sBurst;
    }

    public void setSBurst(boolean sb) {
        this.sBurst = sb;
    }

    public boolean isBurstAtt() {
        return burstAtt;
    }

    public void setBurstAtt(boolean ba, int CPH) {
        this.burstAtt = ba;
        this.SAtt = CPH;
    }

    public void setBurstAtt(boolean ba) {
        this.burstAtt = ba;
        this.SAtt = 0;
    }

    public int getSAtt() {
        return SAtt;
    }

    public boolean isBurstDef() {
        return burstDef;
    }

    public void setBurstDef(boolean bd) {
        this.burstDef = bd;
    }

    public void addLine() {
        pane.addLine(prepareSet.takeBobos());
    }

    public LinkedList<Bobo> getAllBobo(int boboState) {
        return pane.getAllBobo(boboState);
    }

    @Override
    public void handle(OpEvent e) {
        //level judge!
        if (e.isP1() != p1) {
            return;
        }
        if (!e.isReleased()) {
            switch (Config.p1Controller.indexOf(e.getKc())) {
                case Config.left:
                    moveBrickL();
                    break;
                case Config.right:
                    moveBrickR();
                    break;
                case Config.down:
                    moveBrickD();
                    break;
                case Config.swap:
                    swap();
                    break;
                case Config.sBurst:
                    pressS();
                    break;
                case Config.sDef:
                    burstSD();
                    break;
                case Config.sAtt:
                    burstSA();
                    break;
                case Config.stop:
                    stop();
                    break;
                default:
                    break;
            }

            switch (Config.p2Controller.indexOf(e.getKc())) {
                case Config.left:
                    moveBrickL();
                    break;
                case Config.right:
                    moveBrickR();
                    break;
                case Config.down:
                    moveBrickD();
                    break;
                case Config.swap:
                    swap();
                    break;
                case Config.sBurst:
                    pressS();
                    break;
                case Config.sDef:
                    burstSD();
                    break;
                case Config.sAtt:
                    burstSA();
                    break;
                case Config.stop:
                    stop();
                    break;
                default:
                    break;
            }
        } else {

            switch (Config.p1Controller.indexOf(e.getKc())) {
                case Config.down:
                    releasedD();
                    break;
                case Config.sBurst:
                    releasedS();
                    break;
                case Config.swap:
                    releasedSwap();
                    break;
                default:
                    break;
            }
            switch (Config.p2Controller.indexOf(e.getKc())) {
                case Config.down:
                    releasedD();
                    break;
                case Config.sBurst:
                    releasedS();
                    break;
                case Config.swap:
                    releasedSwap();
                    break;
                default:
                    break;
            }
        }
    }

    private void moveBrickL() {
        if (brick != null && enable) {
            if (brick.getD().getLeft() != null && brick.getD().getLeft().isNull()) {
                if (brick.moveL()) {
                    Config.effectMedia.play(0);
                }
            }
            this.pane.getShadow().setIndex(brick.getD().getIndexX());
        }
    }

    private void moveBrickR() {
        if (brick != null && enable) {
            if (brick.getD().getRight() != null && brick.getD().getRight().isNull()) {
                if (brick.moveR()) {
                    Config.effectMedia.play(0);
                }
            }
            this.pane.getShadow().setIndex(brick.getD().getIndexX());
        }
    }

    private void moveBrickD() {
        if (brick != null && enable) {
            if (sp.getSpeed() < 10) {
                speedTemp = sp.getSpeed();
            }
            sp.setSpeed(16);
        }
    }

    private void swap() {
        if (this.swapDown) {
            return;
        }
        if (brick != null && enable) {
            brick.swap();
            this.swapDown = true;
        }
    }

    private void pressS() {

        if (this.getMainModel().isEnd()) {
            if(this.getMainModel().isNetwork())
            Config.controller.end(this.getMainModel().isHost());
            else  Config.controller.end();
            Config.menuController.ReturnToMenu();
            this.getMainModel().again();
            return;
        }
        if (sDown) {
            return;
        }
        if (counterSet.isShinning()) {
            this.setEnable(false);
            // 暗
            this.getPane().getChildren().add(this.getPane().getSTip());
            this.sDown = true;
        }
    }

    private void burstSD() {
        if (sDown && counterSet.isShinning()) {
            counterSet.setShinning(false);
            this.sBurst = true;
            this.burstDef = true;
            Config.effectMedia.play(15);
        }
    }

    private void burstSA() {
        //test
        if (sDown && counterSet.isShinning()) {
            Config.effectMedia.play(14);
            this.sattCPToRival = 0;
            if (this.mm.isNetwork()) {
                this.sattCPToRival = this.counterSet.useCP();
            } else {
                this.mm.SAtt(!p1, this.counterSet.useCP());
            }
        }
    }

    private void stop() {

    }

    private void releasedS() {
        if (sp.getState() == Splash.MOVING) {
            this.setEnable(true);
        }
        this.getPane().getChildren().remove(this.getPane().getSTip());
        this.sDown = false;
    }

    private void releasedD() {
        sp.setSpeed(speedTemp);
    }

    private void releasedSwap() {
        this.swapDown = false;
    }

    public void setWin(boolean win) {
//        controller.send(win);
//        if(this.network){
//            this.sp.getTimeline().stop();
//            return;
//        }
        this.sp.getTimeline().stop();
        Config.bgmMedia.stopMusic();
        if (win) {
            Config.effectMedia.play(10);
            this.getPane().getWol().setImage(Config.getEffects().get(10));
        } else {
            Config.effectMedia.play(11);
            this.getPane().getWol().setImage(Config.getEffects().get(11));
        }
        ImageView iv = this.getPane().getWol();
        iv.setTranslateX(this.getPane().getTranslateX());
        iv.setTranslateY(this.getPane().getTranslateY());
        if (!this.getMainFrame().getChildren().contains(iv)) {
            this.getMainFrame().getChildren().add(iv);
        } else {
            System.out.println("?dulicate");
            System.out.println(this.p1);
        }
        this.enable = false;

        this.win = true;
        if (!win) {
            if (this.mm.isNetwork()) {
                this.win = false;
            } else
           this.getMainModel().winner(!p1);
        }
    }

    //这是一个发出信息的示例
    public synchronized void send(boolean first) {
        if (first) {
            Config.controller.send(new FirstMessage(this.getPersonage()));
            return;
        }
        ArrayList<Node> cops = new ArrayList<>();
        cops.addAll(this.pane.getChildren());
        ArrayList<Node> comf = new ArrayList<>();
        comf.addAll(this.mf.getChildren());
        ArrayList<Node> coqp = new ArrayList<>();
        coqp.addAll(this.prepareSet.getQp().getChildren());
        ArrayList<Node> corp = new ArrayList<>();
        corp.addAll(this.prepareSet.getRp().getChildren());

        ArrayList<Node> temp = new ArrayList<>();
        if (p1) {
            for (Node n : this.mf.getChildren()) {
                if (n.getTranslateX() >= 0) {
                    temp.add(n);
                }
            }
        } else {
            for (Node n : this.mf.getChildren()) {
                if (n.getTranslateX() <= 0) {
                    temp.add(n);
                }
            }
        }
        comf.removeAll(temp);
        comf.remove(this.pane);
        comf.remove(this.counterSet.getCpc().getContent());
        comf.remove(this.counterSet.getLc().getContent());
        comf.remove(this.prepareSet.getQp());
        comf.remove(this.prepareSet.getRp());

        cops.remove(this.getPane().getChildren().get(0));
        cops.remove(this.getPane().getChildren().get(1));
         
        Config.controller.send(new UpdataMessage(new UpdataEvent(p1, cops, comf, coqp, corp, this.counterSet.getCpc().getNowCP(), this.counterSet.getLc().getLines(), sattCPToRival, win)));
        sattCPToRival = 0;
        win = true;
    }

    public synchronized void recv(Object o) {
        //这个是被调用的，每次被调用就会接收到对方发出的对象
        //这然这里是要根据对方的对象的类型来对这个model(p2)进行修改和更新
        if (o instanceof FirstMessage) {
            //     this.setPersonage(((FirstMessage)o).getCharacter());
            this.mm.upData(new UpdataEvent("clear"));
            return;
        }
        UpdataEvent ue = new UpdataEvent((UpdataMessage) o);
       
       this.getMainModel().upData(ue);
    }

   
}
