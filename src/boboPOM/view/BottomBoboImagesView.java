package boboPOM.view;

import boboPOM.config.Config;
import javafx.scene.layout.Region;

/**
 * Created by Jeremie on 14-3-5.
 */
public class BottomBoboImagesView extends Region {
    private BoboImageView[] bottomImages = new BoboImageView[7];


    public BottomBoboImagesView() {
        for (int i = 0; i < 7; i++) {
            bottomImages[i] = new BoboImageView();
            bottomImages[i].setLayoutX(Config.BOTTOMBOBO_INTERVAL* i);
            bottomImages[i].setVisible(true);
            this.getChildren().add(bottomImages[i]);
        }
        this.setVisible(true);
    }
}
