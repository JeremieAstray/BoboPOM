package boboPOM.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    private static final String AppCss = Main.class.getResource("resources/css/style.css").toExternalForm();

    /**
     * 主程序
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Bobobumps.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(AppCss);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
