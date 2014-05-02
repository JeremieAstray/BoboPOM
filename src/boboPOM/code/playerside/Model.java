
package boboPOM.code.playerside;

import boboPOM.config.Config;
import boboPOM.controller.Controller;
import boboPOM.view.main.MainFrame;
import boboPOM.view.main.MainModel;
import boboPOM.view.main.Splash;
import boboPOM.code.basic.Bobo;
import boboPOM.code.basic.Brick;
import boboPOM.controller.OpEvent;
import boboPOM.code.counters.CounterSet;
import boboPOM.code.prepareContainer.PrepareSet;
import boboPOM.view.main.UpdataEvent;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.LinkedList;
import javafx.collections.ObservableList;
import javafx.scene.Node;

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
    private MainModel mm;

    public Model(boolean p1,boolean network) {
        this.p1 = p1;
        counterSet = new CounterSet(p1);
        prepareSet = new PrepareSet(p1);
        personage = Config.Robozy;
        deep = 0;
        pane = new PlayerSide(p1, personage);
        sp = new Splash(this,network);
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
    
    public void setMainModel(MainModel mm){
        this.mm = mm;
    }
    
    public MainModel getMainModel(){
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
        this.pane.getShadow().setWarning(deep>=8);
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
        if(!pane.getBobo(3, 12).isNull()) return false;
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

    public void setShadowIn(boolean enable){
          if(!enable) this.pane.getShadow().out();
        else this.pane.getShadow().setIndex(this.brick.getD().getIndexX());
    }
    public boolean isSBurst() {
        return sBurst;
    }
    
public void setSBurst(boolean sb){
    this.sBurst = sb;
}
    public boolean isBurstAtt() {
        return burstAtt;
    }

    public void setBurstAtt(boolean ba,int CPH){
        this.burstAtt = ba;
        this.SAtt = CPH;
    }
    
       public void setBurstAtt(boolean ba){
        this.burstAtt = ba;
        this.SAtt = 0;
    }
    
    public int  getSAtt(){
        return SAtt;
    }
    public boolean isBurstDef() {
        return burstDef;
    }

    public void setBurstDef(boolean bd){
        this.burstDef = bd;
    }
    
    public void addLine() {
        pane.addLine(prepareSet.takeBobos());
    }

    public  LinkedList<Bobo> getAllBobo(int boboState) {
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
        if (brick != null&&enable) {
            if (brick.getD().getLeft() != null && brick.getD().getLeft().isNull()) {
                brick.moveL();
            }
            this.pane.getShadow().setIndex(brick.getD().getIndexX());
        }
    }

    private void moveBrickR() {
        if (brick != null&&enable) {
            if (brick.getD().getRight() != null && brick.getD().getRight().isNull()) {
                brick.moveR();
            }
             this.pane.getShadow().setIndex(brick.getD().getIndexX());
        }
    }

    private void moveBrickD() {
        if (brick != null&&enable) {
           sp.setSpeed(16);
        }
    }

    private void swap() {
        if(this.swapDown) return;
        if (brick != null&&enable) {
            brick.swap();
            this.swapDown = true;
        }
    }

    private void pressS() {
        
        if(this.getMainModel().isEnd()){
            this.getMainModel().again();
            return;
        }
        if(sDown) return;
        if (counterSet.isShinning()) {
            this.setEnable(false);
            // 暗
            this.getPane().getChildren().add(this.getPane().getSTip());
            this.sDown = true;
        }
    }

    private void burstSD() {
        if (sDown&&counterSet.isShinning()) {
            this.sBurst = true;
            this.burstDef = true;
        }
    }

    private void burstSA() {
           //test
        if(sDown&&counterSet.isShinning()){
            this.mm.SAtt(!p1,this.counterSet.useCP());
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
            sp.setSpeed(1.5);
    }
    
    private void releasedSwap(){
        this.swapDown = false;
    }

    public void setWin(boolean win) {
//        controller.send(win);
        if (win) {
            this.getPane().getWol().setImage(Config.getEffects().get(10));
        } else {
            this.getPane().getWol().setImage(Config.getEffects().get(11));
        }
        ImageView iv = this.getPane().getWol();
        iv.setTranslateX(this.getPane().getTranslateX());
        iv.setTranslateY(this.getPane().getTranslateY());       
        this.getMainFrame().getChildren().add(iv);
        this.enable = false;
        this.sp.getTimeline().stop();
        if(!win)
        this.getMainModel().winner(!p1);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private Controller controller;

   
    //这是一个发出信息的示例
        public void send(){
            ObservableList<Node> cops = this.pane.getChildren();
            ObservableList<Node> comf = this.mf.getChildren(); 
            ArrayList<Node> temp = new ArrayList<>();
            if(p1)
            for(Node n : this.mf.getChildren()){
                if(n.getTranslateX()>=0){
                    temp.add(n);
                }
            }
            else
               for(Node n : this.mf.getChildren()){
                if(n.getTranslateX()<=0){
                    temp.add(n);
                }
            }
            comf.removeAll(temp);
            comf.remove(this.pane);
            controller.send(new UpdataEvent(p1,cops,comf));
        }
     

    public void recv(Object o){
        //这个是被调用的，每次被调用就会接收到对方发出的对象
        //这然这里是要根据对方的对象的类型来对这个model(p2)进行修改和更新
        UpdataEvent ue = (UpdataEvent)o;
        this.getMainModel().upData(ue);
    }

}
