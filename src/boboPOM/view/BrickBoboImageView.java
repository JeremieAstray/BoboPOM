package boboPOM.view;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * Created by Jeremie on 14-3-5.
 */
public class BrickBoboImageView extends Region {

    private ImageView boboTop;
    private ImageView boboButtom;
    private ImageView boboLocation;

    public BrickBoboImageView(){
        boboTop = new ImageView();
        boboButtom = new ImageView();
        boboLocation = new ImageView();
        this.getChildren().addAll(boboTop,boboButtom,boboLocation);
    }

}
