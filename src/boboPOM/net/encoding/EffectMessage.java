/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boboPOM.net.encoding;

import boboPOM.config.Config;
import java.util.ArrayList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.ImageView;
import javax.swing.JFrame;

/**
 *
 * @author Administrator
 */
public class EffectMessage extends Message{// network use
    private int index;
    public ArrayList<ImageView> ivs = new ArrayList<>();
    public EffectMessage(ImageView iv){
        super(iv);
        this.index = Config.getEffects().indexOf(iv.getImage());
//        if(index < 0){
//            ivs.add(iv);
//        }
    }

    public int getIndex() {
        return index;
    }
    
}
