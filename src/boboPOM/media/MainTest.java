/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boboPOM.media;

import boboPOM.config.Config;
import boboPOM.view.Main;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * @author Administrator
 */
public class MainTest extends Application {

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Config.init();
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Config.effectMedia.play(13);
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);
        StackPane sp = new StackPane();
        ImageView iv = new ImageView(Config.getBackgrounds().get(0));
        ArrayList<Image> li = new ArrayList<>();
        li.add(new Image(Main.class.getResourceAsStream("resources/images/background/background.png")));
        System.out.println(li.contains(iv.getImage()));
        sp.getChildren().add(iv);
        root.getChildren().add(sp);
        System.out.println(sp instanceof Node);
        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
