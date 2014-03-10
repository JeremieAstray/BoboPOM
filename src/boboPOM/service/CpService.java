package boboPOM.service;

import boboPOM.Entity.CpEntity;
import boboPOM.util.MsgQueue;

/**
 * Created by Jeremie on 14-3-10.
 */
public class CpService extends BaseService {

    private MsgQueue<CpEntity> cpMsg;
    private CpEntity cpEntity;

    public CpService(){
        super();
        cpEntity = new CpEntity();
        cpMsg = new MsgQueue<CpEntity>();
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
}
