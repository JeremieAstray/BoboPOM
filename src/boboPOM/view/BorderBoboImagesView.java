package boboPOM.view;

import boboPOM.config.Config;
import javafx.scene.layout.Region;

/**
 * Created by Jeremie on 14-3-5.
 */
public class BorderBoboImagesView extends Region {
    private BoboImageView[] borderImages = new BoboImageView[10];

    public BorderBoboImagesView() {
        for (int i = 0; i < 10; i++) {
            borderImages[i] = new BoboImageView();
            borderImages[i].setLayoutY(Config.BORDERBOBO_INTERVAL * i);
            borderImages[i].setVisible(true);
            this.getChildren().add(borderImages[i]);
        }
        this.setVisible(true);
    }
}
