/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boboPOM.net.encoding;

import boboPOM.net.encoding.Message;
import boboPOM.net.encoding.EffectMessage;
import boboPOM.config.Config;
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 *
 * @author Administrator
 */
public class PaneMessage extends Message{// network use
    private int chainsT,chainsC,bounsT,bounsC;
    private ArrayList<EffectMessage> ems;
    
    public PaneMessage(Pane p){
        super(p);
        for(Node iv : p.getChildren()){
            ems.add(new EffectMessage((ImageView)iv));
        }  
        this.chainsT = Config.getNumbers().get(2).indexOf(((ImageView)p.getChildren().get(1)).getImage());
        this.chainsC =  Config.getNumbers().get(2).indexOf(((ImageView)p.getChildren().get(2)).getImage());
        this.bounsT = Config.getNumbers().get(2).indexOf(((ImageView)p.getChildren().get(3)).getImage());
        this.bounsC =  Config.getNumbers().get(2).indexOf(((ImageView)p.getChildren().get(4)).getImage());
    }

    public int getChainsT() {
        return chainsT;
    }

    public int getChainsC() {
        return chainsC;
    }

    public int getBounsT() {
        return bounsT;
    }

    public int getBounsC() {
        return bounsC;
    }

    public ArrayList<EffectMessage> getEms() {
        return ems;
    }


    
}
