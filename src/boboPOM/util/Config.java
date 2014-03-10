package boboPOM.util;

import javafx.util.Duration;

/**
 * Created by Jeremie on 14-3-4.
 */
public final class Config {

    public final static int NONE = 0;
    public final static int BLUE = 1;
    public final static int RED = 2;
    public final static int GREEN = 3;
    public final static int YELLOW = 4;
    public final static int WHITE = 5;

    public static final int PORT = 7001;//5918

    public static final double SCREEN_WIDTH = 960.0;//pixel
    public static final double SCREEN_HEIGHT = 540.0;//pixel

    public static final Duration ANIMATION_TIME = Duration.millis(40);//ms

    public static final double PLAYBOBO_SPEED = 10;//pixel

    public static final double BORDERBOBO_INTERVAL = 46;//pixel
    public static final double BOTTOMBOBO_INTERVAL = 44;//pixel
    public static final double CENTREBOBO_INTERVALX = 44, CENTREBOBO_INTERVALY = 40.4;//pixel
}
