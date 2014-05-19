package boboPOM.view;

import boboPOM.config.Config;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
//import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * 主程序
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Config.init();
        Config.stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("BoboPom.fxml"));
        Config.root = root;
        primaryStage.setMaxHeight(Config.SCREEN_HEIGHT + 38);
        primaryStage.setMaxWidth(Config.SCREEN_WIDTH);
//        setResulation(Config.getSCREEN_WIDTH(), Config.getSCREEN_HEIGHT());
        Scene scene = new Scene(root);
        primaryStage.setResizable(true);
        primaryStage.setTitle("BoboPom!");
        primaryStage.getIcons().add(new Image(Main.class.getResource("resources/images/title.png").toExternalForm()));
        primaryStage.setScene(scene);
        primaryStage.show();

    }

//    public void setResulation(double x, double y) {
//        Config.stage.setMaxHeight(y + 38);
//        Config.stage.setMaxWidth(x);
//        Config.root.setScaleX(x / (Config.SCREEN_WIDTH));
//        Config.root.setScaleY(y / (Config.SCREEN_HEIGHT));
//    }

    public static void main(String[] args) {
        launch(args);
    }
}
