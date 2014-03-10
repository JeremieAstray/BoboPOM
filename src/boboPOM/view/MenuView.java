package boboPOM.view;

import javafx.scene.layout.Region;
import javafx.scene.text.TextFlow;

/**
 * Created by Jeremie on 14-3-7.
 */
public class MenuView extends Region {

    private TextFlow option1;
    private TextFlow option2;
    private TextFlow option3;

    public MenuView(){
        option1 = new TextFlow();
        option2 = new TextFlow();
        option3 = new TextFlow();
        this.setVisible(false);
    }
}
