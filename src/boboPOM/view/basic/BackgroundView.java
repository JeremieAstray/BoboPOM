package boboPOM.view.basic;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * Created by Jeremie on 14-3-5.
 */
public class BackgroundView extends Region {
    private ImageView Background = new ImageView();

    private ImageView leftCharacter = new  ImageView();

    private ImageView leftCpHundreds = new ImageView(); //百位
    private ImageView leftCpDecade = new ImageView();   //十位
    private ImageView leftCpUnit = new ImageView();     //个位
    private ImageView leftCpBottom = new ImageView();
    private ImageView leftCpBottomEx = new ImageView();
    private ImageView leftCpProgressBar = new ImageView();
    private ImageView leftLineup = new ImageView();


    private ImageView rightCharacter = new  ImageView();
    private ImageView rightCpHundreds = new ImageView();
    private ImageView rightCpDecade = new ImageView();
    private ImageView rightCpUnit = new ImageView();
    private ImageView rightCpBottom = new ImageView();
    private ImageView rightCpBottomEx = new ImageView();
    private ImageView rightCpProgressBar = new ImageView();
    private ImageView rightLineup = new ImageView();

    public BackgroundView() {
        Background.setVisible(true);
        this.setPartsVisible(false);
        this.getChildren().addAll(Background
                , leftCpHundreds, leftCpDecade, leftCpUnit, leftCpBottom,leftCpBottomEx, leftCpProgressBar, leftLineup
                , rightCpHundreds, rightCpDecade, rightCpUnit, rightCpBottom,rightCpBottomEx, rightCpProgressBar, rightLineup);
    }

    public final void setPartsVisible(boolean value) {
        leftCpHundreds.setVisible(value);
        leftCpDecade.setVisible(value);
        leftCpUnit.setVisible(value);
        leftCpBottom.setVisible(value);
        leftCpBottomEx.setVisible(value);
        leftCpProgressBar.setVisible(value);
        leftLineup.setVisible(value);
        rightCpHundreds.setVisible(value);
        rightCpDecade.setVisible(value);
        rightCpUnit.setVisible(value);
        rightCpBottom.setVisible(value);
        rightCpBottomEx.setVisible(value);
        rightCpProgressBar.setVisible(value);
        rightLineup.setVisible(value);
    }

    public void setBackgroundImage(Image image){
        Background.setImage(image);
    }
}
