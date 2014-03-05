package boboPOM.view;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Jeremie on 14-3-5.
 */
public class BoboExtendsView extends Region {

    private Vector<ImageView> whiteBoboExtendImage = new Vector<ImageView>();

    public BoboExtendsView() {
        this.setVisible(true);
    }

    public void addWhiteBoboExtend(ImageView imageView){
        whiteBoboExtendImage.add(imageView);
        this.getChildren().addAll(imageView);
    }

    public void removeWhiteBoboExtend(ImageView imageView){
        whiteBoboExtendImage.remove(imageView);
        this.getChildren().remove(imageView);
    }


}
