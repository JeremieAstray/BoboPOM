package boboPOM.config;

import boboPOM.controller.Controller;
import boboPOM.controller.MenuController;
import boboPOM.media.BGMMedia;
import boboPOM.media.EffectMedia;
import boboPOM.net.SocketLink;
import boboPOM.view.Main;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Jeremie on 14-3-4.
 */
public final class Config {

    public static final int NULL = -1;
    public static final int TYPE_YELLOW = 0;
    public static final int TYPE_RED = 1;
    public static final int TYPE_BLUE = 2;
    public static final int TYPE_GREEN = 3;
    public static final int TYPE_WHITE = 4;
    public static final int PORT = 7001;//5918
    public static final int SCREEN_WIDTH = 960;//pixel
    public static final int SCREEN_HEIGHT = 540;//pixel
    public static final Duration ANIMATION_TIME = Duration.millis(40);//ms
    public static final int CP_MAX = 999;
    public static final int CP_MIN = 0;
    public static final int LINE_MAX = 16;
    public static final int LINE_MIN = 0;
    public static final int left = 0, right = 1, down = 2, swap = 3, sBurst = 4, sDef = 5, sAtt = 6, stop = 7;
    public static final int Robozy = 0;
    public static final int Royde = 1;
    public static final int Fran = 2;
    public static final int Michelle = 3;
    public static final int Jona = 4;
    public static final int Abbas = 5;
    public static final int MariaBell = 6;
    public static final int Tio = 7;
    public static final int Campaleila = 8;
    public static final double BoboSizeW = 44;
    public static final double BoboSizeH = 44;
    public static Stage stage;
    public static Parent root;
    public static Controller controller;
    public static MenuController menuController;
    public static final BGMMedia bgmMedia = new BGMMedia();
    public static  final EffectMedia effectMedia = new EffectMedia();
    public static InputStreamReader inputStreamReader;
    public static SocketLink socketLink;
    public static ArrayList<KeyCode> p1Controller, p2Controller;
    public static boolean network = false;
    private static ArrayList<ArrayList<Image>> bobosImages;
    private static ArrayList<Image> effects;
    private static ArrayList<ArrayList<Image>> numbers;
    private static ArrayList<Image> backgrounds;
    private static ArrayList<Image> personages;
    private static ArrayList<Image> memuImages;
    private static File stateFile;

    public static ArrayList<ArrayList<Image>> getBobosImages() {
        return bobosImages;
    }

    public static ArrayList<Image> getEffects() {
        return effects;
    }

    public static ArrayList<ArrayList<Image>> getNumbers() {
        return numbers;
    }

    public static ArrayList<Image> getBackgrounds() {
        return backgrounds;
    }

    public static ArrayList<Image> getPersonages() {
        return personages;
    }

    public static ArrayList<Image> getMemuImages() {
        return memuImages;
    }

    public static File getStateFile() {
        return stateFile;
    }

    public static int getSCREEN_WIDTH() {
        return SCREEN_WIDTH;
    }

    public static int getSCREEN_HEIGHT() {
        return SCREEN_HEIGHT;
    }

    public static void init() {
        //music
        for (int i = 0; i < 3; i++) {
            bgmMedia.addURL(Main.class.getResource("/media/BGM/ed758" + (7 + i) + ".wav"));
        }

        for (int i = 0; i < 16; i++) {
            if (i < 10) {
                effectMedia.addEffect(Main.class.getResource("/media/sound_effects/ed7s000" + i + ".wav"));
            } else if (i >= 10 && i < 100) {
                effectMedia.addEffect(Main.class.getResource("/media/sound_effects/ed7s00" + i + ".wav"));
            }
        }
        //background
        backgrounds = new ArrayList<>();
        backgrounds.add(new Image(Main.class.getResourceAsStream("resources/images/background/background.png")));
        backgrounds.add(new Image(Main.class.getResourceAsStream("resources/images/background/underBackground.png")));
        backgrounds.add(new Image(Main.class.getResourceAsStream("resources/images/background/main.png")));
        //boboImages
        bobosImages = new ArrayList<>();
        ArrayList<Image> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list = new ArrayList<>();
            String type;
            switch (i) {
                case Config.TYPE_YELLOW:
                    type = "yellow";
                    break;
                case Config.TYPE_BLUE:
                    type = "blue";
                    break;
                case Config.TYPE_GREEN:
                    type = "green";
                    break;
                case Config.TYPE_RED:
                    type = "red";
                    break;
                case Config.TYPE_WHITE:
                    type = "white";
                    break;
                default:
                    System.out.println("init no this color");
                    type = "?";
            }
            for (int j = 0; j < 8; j++) {
                list.add(new Image(Main.class.getResourceAsStream("resources/images/bobo/" + type + "/"
                        + (j + 1) + ".png")));
            }
            bobosImages.add(list);
        }

        //personage
        personages = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            personages.add(new Image(Main.class.getResourceAsStream("resources/images/personage/" + i + ".png")));
        }
        //effect
        effects = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            effects.add(new Image(Main.class.getResourceAsStream("resources/images/image_effects/" + i + ".png")));
        }
        //numbers
        numbers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list = new ArrayList<>();
            String name = null;
            switch (i) {
                case 0:
                    name = "CP";
                    break;
                case 1:
                    name = "CPH";
                    break;
                case 2:
                    name = "chains";
                    break;
                case 3:
                    name = "lines";
                    break;
                default:
                    System.out.println("init no this number");
            }
            for (int j = 0; j < 10; j++) {
                list.add(new Image(Main.class.getResourceAsStream("resources/images/number/"
                        + name + "/" + j + ".png")));
            }
            numbers.add(list);
        }
        //menu
        memuImages = new ArrayList<Image>();
        for (int i = 0; i < 5; i++) {
            memuImages.add(new Image(Main.class.getResourceAsStream("resources/images/"
                    + "menu/" + i + ".png")));
        }
        InputStream inputStream = Main.class.getResourceAsStream("/boboPOM/view/resources/images/menu/statement.txt");
        try {
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        } catch (UnsupportedEncodingException ex) {
            System.err.println("Config UnsupportedEncodingException");
            ex.printStackTrace();
        }
    }
}
