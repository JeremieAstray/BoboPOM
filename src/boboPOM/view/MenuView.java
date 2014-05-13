package boboPOM.view;

import boboPOM.config.Config;
import boboPOM.controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by Jeremie on 2014/5/13.
 */
public class MenuView extends AnchorPane {

    private Controller controller;

    public MenuView() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("menu/menu.fxml"));
        ImageView background = new ImageView(new Image(Main.class.getResourceAsStream("resources/images/background/main.png")));
        background.setFitHeight(Config.getSCREEN_HEIGHT());
        background.setFitWidth(Config.getSCREEN_WIDTH());
        this.getChildren().addAll(background,root);
    }

}
