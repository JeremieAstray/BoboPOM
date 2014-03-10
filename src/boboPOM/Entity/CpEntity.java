package boboPOM.Entity;

import java.io.Serializable;

/**
 * Created by Jeremie on 14-3-4.
 */
public class CpEntity implements Serializable {

    private int cp;

    public CpEntity() {
        cp = 0;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }
}
