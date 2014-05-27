package boboPOM.view;

import boboPOM.config.Config;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

//import javafx.scene.layout.Pane;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

//    public void setResulation(double x, double y) {
//        Config.stage.setMaxHeight(y + 38);
//        Config.stage.setMaxWidth(x);
//        Config.root.setScaleX(x / (Config.SCREEN_WIDTH));
//        Config.root.setScaleY(y / (Config.SCREEN_HEIGHT));
//    }

    /**
     * 主程序
     *
     * @param primaryStage
     *
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
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent arg0) {
                System.exit(0);
            }
        });
        primaryStage.show();
    }
}
