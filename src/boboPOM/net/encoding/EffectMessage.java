/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boboPOM.net.encoding;

import boboPOM.config.Config;
import javafx.scene.image.ImageView;

/**
 *
 * @author Administrator
 */
public class EffectMessage extends Message{// network use
    private int index;
    public EffectMessage(ImageView iv){
        super(iv);
        this.index = Config.getEffects().indexOf(iv.getImage());
    }

    public int getIndex() {
        return index;
    }
    
}
