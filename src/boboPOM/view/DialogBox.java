package boboPOM.view;

import javafx.scene.layout.Region;
import javafx.scene.text.Text;

/**
 * Created by Jeremie on 14-3-7.
 */
public class DialogBox extends Region {

    private Text text;

    public DialogBox(){
        text = new Text();
        this.setVisible(false);
    }
}
