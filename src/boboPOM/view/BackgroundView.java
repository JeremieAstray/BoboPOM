package boboPOM.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * Created by Jeremie on 14-3-5.
 */
public class BackgroundView extends Region {
    private ImageView Background = new ImageView();

    private ImageView leftCpHundreds = new ImageView();
    private ImageView leftCpDecade = new ImageView();
    private ImageView leftCpUnit = new ImageView();
    private ImageView leftCpControl = new ImageView();
    private ImageView leftCpProgressBar = new ImageView();
    private ImageView leftLineup = new ImageView();

    private ImageView rightCpHundreds = new ImageView();
    private ImageView rightCpDecade = new ImageView();
    private ImageView rightCpUnit = new ImageView();
    private ImageView rightCpControl = new ImageView();
    private ImageView rightCpProgressBar = new ImageView();
    private ImageView rightLineup = new ImageView();

    public BackgroundView() {
        Background.setVisible(true);
        this.setPartsVisible(false);
        this.getChildren().addAll(Background
                , leftCpHundreds, leftCpDecade, leftCpUnit, leftCpControl, leftCpProgressBar, leftLineup
                , rightCpHundreds, rightCpDecade, rightCpUnit, rightCpControl, rightCpProgressBar, rightLineup);
    }

    public final void setPartsVisible(boolean value) {
        leftCpHundreds.setVisible(value);
        leftCpDecade.setVisible(value);
        leftCpUnit.setVisible(value);
        leftCpControl.setVisible(value);
        leftCpProgressBar.setVisible(value);
        leftLineup.setVisible(value);
        rightCpHundreds.setVisible(value);
        rightCpDecade.setVisible(value);
        rightCpUnit.setVisible(value);
        rightCpControl.setVisible(value);
        rightCpProgressBar.setVisible(value);
        rightLineup.setVisible(value);
    }

    public void setBackgroundImage(Image image){
        Background.setImage(image);
    }
}
