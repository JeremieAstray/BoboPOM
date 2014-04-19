/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boboPOM.view.menu;

import boboPOM.config.Config;
import boboPOM.view.Main;
import javafx.application.Application;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author:feng
 */
public class Menu extends Application{

    private static final String AppCss = Main.class.getResource("resources/css/menuStyle.css").toExternalForm();
    /**
     * 主程序
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        primaryStage.setMaxHeight(Config.SCREEN_HEIGHT + 28);
        primaryStage.setMaxWidth(Config.SCREEN_WIDTH + 6);
        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setTitle("BoboPom!");
        primaryStage.setScene(scene);
        scene.getStylesheets().add(AppCss);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
