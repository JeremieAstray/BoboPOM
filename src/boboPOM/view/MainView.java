package boboPOM.view;

import boboPOM.view.main.MainFrame;
import boboPOM.view.main.MainModel;
import boboPOM.code.controllers.Controller;
import javafx.scene.layout.StackPane;

/**
 * Created by Jeremie on 2014/4/19.
 */
public class MainView extends StackPane {

    private MainFrame mainFrame;

    public MainView() {
        Controller.root = this;
        mainFrame = new MainFrame(new MainModel());
        this.layout();
        this.setTranslateY(6);
        mainFrame.getChildren().get(0).requestFocus();
        this.getChildren().add(mainFrame);

    }

}
