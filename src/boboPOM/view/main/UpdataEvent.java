package boboPOM.view.main;

import boboPOM.net.encoding.UpdataMessage;
import boboPOM.net.encoding.PaneMessage;
import boboPOM.net.encoding.Message;
import boboPOM.net.encoding.EffectMessage;
import boboPOM.net.encoding.BoboMessage;
import boboPOM.code.basic.Bobo;
import boboPOM.config.Config;
import java.util.ArrayList;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 *
 * @author yorlbgy
 */
public final class UpdataEvent extends Event {

    private boolean p1;
    private ArrayList<Node> cops;
    private ArrayList<Node> comf;
    private ArrayList<Node> coqp;
    private ArrayList<Node> corp;
    private String command;
    private int cp, lines;
    private int sattCPToRival;
    private boolean win;

    public UpdataEvent(EventType<? extends Event> arg0) {// network use
        super(arg0);
    }

    public UpdataEvent(boolean p1, ArrayList<Node> cops, ArrayList<Node> comf, ArrayList<Node> coqp, ArrayList<Node> corp, int cp, int lines,int sattCPToRival,boolean win) {
        this(EventType.ROOT);
        this.p1 = p1;
        this.cops = cops;
        this.comf = comf;
        this.coqp = coqp;
        this.corp = corp;
        this.cp = cp;
        this.lines = lines;
        this.sattCPToRival = sattCPToRival;
        this.win = win;
        this.command = "updata";
    }

    public UpdataEvent(UpdataMessage um) {
        super(EventType.ROOT);
        this.cops = new ArrayList<>();
        this.comf = new ArrayList<>();
        this.coqp = new ArrayList<>();
        this.corp = new ArrayList<>();
        changeToEvent(um);
        this.command = "updata";
    }

    public UpdataEvent(String command) {
        this(EventType.ROOT);
        this.command = command;
    }

    public boolean isP1() {
        return p1;
    }

    public ArrayList<Node> getCops() {
        return cops;
    }

    public ArrayList<Node> getComf() {
        return comf;
    }

    public ArrayList<Node> getCoqp() {
        return coqp;
    }

    public ArrayList<Node> getCorp() {
        return corp;
    }

    public String getCommand() {
        return command;
    }

    public int getCp() {
        return cp;
    }

    public int getLines() {
        return lines;
    }

    public int getSattCPToRival() {
        return sattCPToRival;
    }

    public boolean isWin() {
        return win;
    }

    
    private void changeToEvent(UpdataMessage um) {
        this.p1 = um.isP1();
        cops.add(buildEffectByMessage(um.getEopls().get(0)));
        for (BoboMessage bm : um.getBopls()) {
            Bobo bo = new Bobo(bm);
            cops.add(bo);
        }
        um.getEopls().remove(0);
        for (EffectMessage em : um.getEopls()) {
            ImageView iv = buildEffectByMessage(em);
            cops.add(iv);
        }
        for(EffectMessage em : um.getEomf()){
            ImageView iv = buildEffectByMessage(em);
            this.comf.add(iv);
        }
         for (BoboMessage bm : um.getBomf()) {
            Bobo bo = new Bobo(bm);
            comf.add(bo);
        }
        for (BoboMessage bm : um.getBoqp()) {
            Bobo bo = new Bobo(bm);
            this.coqp.add(bo);
        }
        for (BoboMessage bm : um.getBorp()) {
            Bobo bo = new Bobo(bm);
            this.corp.add(bo);
        }
        this.cp = um.getCp();
        this.lines = um.getLines();
        this.win = um.isWin();
        this.sattCPToRival = um.getSattCPToRival();
        for(PaneMessage pm : um.getPomf()){
            this.comf.add(buildPaneByMessage(pm));
        }
    }

    private ImageView buildEffectByMessage(EffectMessage em) {
        ImageView iv = new ImageView(Config.getEffects().get(em.getIndex()));
        setBasicPro(iv, em);
        return iv;
    }
    
    private StackPane buildPaneByMessage(PaneMessage pm){
        StackPane sp = new StackPane();
        sp.getChildren().add(buildEffectByMessage(pm.getEms().get(0)));
        int[] num = new int[]{pm.getChainsT(),pm.getChainsC(),pm.getBounsT(),pm.getBounsC()};
        for(int i=0;i<4;i++){
            ImageView iv = new ImageView(Config.getNumbers().get(2).get(num[i]));
            setBasicPro(iv,pm.getEms().get(i+1));
            sp.getChildren().add(iv);
        }
        setBasicPro(sp,pm);
        return sp;
    }

    private void setBasicPro(Node n, Message m) {
        n.setTranslateX(m.getTranslataX());
        n.setTranslateY(m.getTranslateY());
        n.setScaleX(m.getScaleX());
        n.setScaleY(m.getScaleY());
        n.setOpacity(m.getOpacity());
        n.setVisible(m.isVisible());
    }
}
