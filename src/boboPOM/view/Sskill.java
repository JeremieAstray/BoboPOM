package boboPOM.view;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * Created by Jeremie on 14-3-5.
 */
public class SSkill extends Region {

    private ImageView grayBackground = new ImageView();
    private ImageView sSkill = new ImageView();

    public SSkill(){
        this.setVisible(false);
        this.getChildren().addAll(grayBackground,sSkill);
    }

}
