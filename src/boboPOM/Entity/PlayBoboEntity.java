package boboPOM.Entity;

import java.io.Serializable;

/**
 * Created by Jeremie on 14-3-4.
 */
public class PlayBoboEntity implements Serializable {

    private int x, y;
    //x,y从下面开始算
    //这个位置是下面波波的位置
    private int[] bobos ;


    public int[] getBobos() {
        return bobos;
    }

    public void setBobos(int[] bobos) {
        this.bobos = bobos;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
