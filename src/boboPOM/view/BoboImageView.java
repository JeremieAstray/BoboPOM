package boboPOM.view;

import boboPOM.config.Config;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * Created by Jeremie on 14-3-5.
 */
public class BoboImageView extends Region {

    private ImageView baseImage;

    public BoboImageView() {
        Image image = new Image(Config.RESOURCES_PATH + "/images/bobo/blue/blueBobo1.png");
        baseImage = new ImageView();
        baseImage.setImage(image);
        this.getChildren().addAll(baseImage);
    }
}
