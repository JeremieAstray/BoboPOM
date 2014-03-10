package boboPOM.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * Created by Jeremie on 14-3-5.
 */
public class BoboImageView extends Region {

    private ImageView baseImage;

    public BoboImageView(){
        Image image = new Image(Main.class.getResourceAsStream("resources/images/bobo/blue/blueBobo1.png"));
        baseImage = new ImageView();
        baseImage.setImage(image);
        this.getChildren().addAll(baseImage);
    }
}
