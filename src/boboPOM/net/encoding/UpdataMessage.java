/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boboPOM.net.encoding;

import boboPOM.net.encoding.PaneMessage;
import boboPOM.net.encoding.EffectMessage;
import boboPOM.net.encoding.BoboMessage;
import boboPOM.code.basic.Bobo;
import boboPOM.view.main.UpdataEvent;
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Administrator
 */
public class UpdataMessage implements java.io.Serializable{// network use
    private boolean p1;
    private ArrayList<BoboMessage> bopls,boqp,borp;
    private ArrayList<EffectMessage> eopls,eomf;
    private ArrayList<PaneMessage> pomf;
    private int cp,lines;
  
    public UpdataMessage(UpdataEvent ue){
        bopls = new ArrayList<>();
        boqp = new ArrayList<>();
        borp = new ArrayList<>();
        eopls = new ArrayList<>();
        eomf = new ArrayList<>();
        pomf = new ArrayList<>();
        changeToMessages(ue);
    }

    private final void changeToMessages(UpdataEvent ue) {
        this.p1 = ue.isP1();
       for(Node node:ue.getCops()){
           if(node instanceof Bobo){
               bopls.add(new BoboMessage((Bobo)node));
           }else if(node instanceof ImageView){
               eopls.add(new EffectMessage((ImageView)node));
           }
       }
       for(Node node:ue.getComf()){
           if(node instanceof StackPane){
               pomf.add(new PaneMessage((Pane) node));
           }else if(node instanceof ImageView){
               eomf.add(new EffectMessage((ImageView) node));
           }
       }
       for(Node node:ue.getCoqp()){
           if(node instanceof Bobo){
               boqp.add(new BoboMessage((Bobo)node));
           }
       }
        for(Node node:ue.getCorp()){
           if(node instanceof Bobo){
               borp.add(new BoboMessage((Bobo)node));
           }
       }
       this.cp = ue.getCp();
       this.lines = ue.getLines();
    }

    public ArrayList<BoboMessage> getBopls() {
        return bopls;
    }

    public ArrayList<BoboMessage> getBoqp() {
        return boqp;
    }

    public ArrayList<BoboMessage> getBorp() {
        return borp;
    }

    public ArrayList<EffectMessage> getEopls() {
        return eopls;
    }

    public ArrayList<EffectMessage> getEomf() {
        return eomf;
    }

    public ArrayList<PaneMessage> getPomf() {
        return pomf;
    }

    public int getCp() {
        return cp;
    }

    public int getLines() {
        return lines;
    }

    public boolean isP1() {
        return p1;
    }
}
