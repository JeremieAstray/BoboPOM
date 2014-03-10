package boboPOM.Entity;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by Jeremie on 14-3-4.
 */
public class MainBoboEntity implements Serializable {

    private int[] standbyMainBobo;

    //放7个波波
    private Vector<Integer> standbyPlayBobo;
    //放2个波波
    private int lineup;

    private int[][] mainBobos;
    //从0开始到15，从0开始到6

    public int[][] getMainBobos() {
        return mainBobos;
    }

    public void setMainBobos(int[][] mainBobos) {
        this.mainBobos = mainBobos;
    }

    public int[] getStandbyMainBobo() {
        return standbyMainBobo;
    }

    public void setStandbyMainBobo(int[] standbyMainBobo) {
        this.standbyMainBobo = standbyMainBobo;
    }

    public Vector<Integer> getStandbyPlayBobo() {
        return standbyPlayBobo;
    }

    public void setStandbyPlayBobo(Vector<Integer> standbyPlayBobo) {
        this.standbyPlayBobo = standbyPlayBobo;
    }

    public int getLineup() {
        return lineup;
    }

    public void setLineup(int lineup) {
        this.lineup = lineup;
    }

}
