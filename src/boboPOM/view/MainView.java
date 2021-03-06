package boboPOM.view;

import boboPOM.code.main.MainFrame;
import boboPOM.code.main.MainModel;
import javafx.scene.control.Control;
import javafx.scene.layout.StackPane;

/**
 * Created by Jeremie on 2014/4/19.
 */
public class MainView extends StackPane {

    private MainFrame mainFrame;
    private Control control;

    public MainView() {

    }


    public void init(boolean host, boolean network) {
        control = new MyControl();
        this.getChildren().addAll(control);
        MainModel mm = new MainModel(host, network);
        mainFrame = new MainFrame(mm);
        this.layout();
        this.setTranslateY(6);
        mainFrame.getChildren().get(0).requestFocus();
        this.getChildren().add(mainFrame);

    }

    public void setPersonages(int p1, int p2) {
        mainFrame.getP1().setPersonage(p1);
        mainFrame.getP2().setPersonage(p2);
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    class MyControl extends Control {

    }
}
