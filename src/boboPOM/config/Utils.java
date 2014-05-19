package boboPOM.config;

/**
 * @author yorlbgy
 */
public class Utils {

    public static int getIndexOfBobo(int x, int y) {
        return x * 16 + y + 3;
    }

    public static boolean lineUp(int timecounter, int deep) {
        return timecounter / 25 > deep * 2 + 2;
    }
}
