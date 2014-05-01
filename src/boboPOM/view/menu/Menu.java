/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boboPOM.view.menu;

import boboPOM.config.Config;
import boboPOM.view.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author:feng
 */
public class Menu extends Application {

    private static final String AppCss = Main.class.getResource("resources/css/menuStyle.css").toExternalForm();

    /**
     * 主程序
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Config.init();
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));

//        Group root = new Group();
//        ImageView background = new ImageView(Config.getBackgrounds().get(2));
//
//        BaseMenuBar baseMenuBar = new BaseMenuBar(200, 70);
//        background.setFitWidth(Config.SCREEN_WIDTH);
//        background.setFitHeight(Config.SCREEN_HEIGHT);
//        MenuItem menuItem = new MenuItem("test", 100, 50);
//        MenuItem menuItem2 = new MenuItem("test", 100, 50);
//        MenuItem menuItem3 = new MenuItem("test", 100, 50);
//        MenuItem menuItem4 = new MenuItem("test", 100, 50);
//        baseMenuBar.addItem(menuItem);
//        baseMenuBar.addItem(menuItem2);
//        baseMenuBar.addItem(menuItem3);
//        baseMenuBar.addItem(menuItem4);
//        menuItem.setLayoutX(100);
//        menuItem.setLayoutY(100);
//        root.getChildren().add(background);
//        root.getChildren().add(baseMenuBar);
        //root.getChildren().add(menuItem);
        primaryStage.setMaxHeight(Config.SCREEN_HEIGHT + 28);
        primaryStage.setMaxWidth(Config.SCREEN_WIDTH + 6);
        Scene scene = new Scene(root);
        //primaryStage.setResizable(false);
        primaryStage.setTitle("BoboPom!");
        primaryStage.setScene(scene);
        scene.getStylesheets().add(AppCss);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
