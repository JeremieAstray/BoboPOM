package boboPOM.view;

import boboPOM.config.Config;
import javafx.scene.layout.Region;

/**
 * Created by Jeremie on 14-3-4.
 */
public class CentreBoboImagesView extends Region {

    private BoboImageView[][] centreImages = new BoboImageView[13][7];


    public CentreBoboImagesView() {
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 7; j++) {
                centreImages[i][j] = new BoboImageView();
                centreImages[i][j].setVisible(true);
                centreImages[i][j].setLayoutX(Config.CENTREBOBO_INTERVALX * j);
                centreImages[i][j].setLayoutY(Config.CENTREBOBO_INTERVALY * i);
                this.getChildren().add(centreImages[i][j]);
            }
        }
    }
}
