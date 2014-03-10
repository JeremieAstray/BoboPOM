package boboPOM.service;

import boboPOM.Entity.CpEntity;
import boboPOM.util.MsgQueue;
import boboPOM.util.SocketLink;

/**
 * Created by Jeremie on 14-3-10.
 */
public class CpService {

    private CpEntity cpEntity;

    public CpService() {
        cpEntity = new CpEntity();
    }

    public void setCp(int cp) {
        if (cp > 999)
            cpEntity.setCp(999);
        else
            cpEntity.setCp(cp);
    }

    public boolean isBiggerThan100() {
        return cpEntity.getCp() > 100;
    }

    public void send(SocketLink socketLink) {
        socketLink.send(this.cpEntity);
    }
}
