package boboPOM.service;

import boboPOM.Entity.PlayBoboEntity;
import boboPOM.util.MsgQueue;

/**
 * Created by Jeremie on 14-3-10.
 */
public class PlayBoboService extends BaseService {

    private MsgQueue<PlayBoboEntity> playBoboMsg;
    private PlayBoboEntity playBoboEntity;

    public PlayBoboService() {
        super();
        playBoboEntity = new PlayBoboEntity();
        playBoboMsg = new MsgQueue<PlayBoboEntity>();
        playBoboEntity.setX(3);
        playBoboEntity.setY(13);
        playBoboEntity.setBobos(new int[2]);
        playBoboEntity.getBobos()[0] = 0;
        playBoboEntity.getBobos()[1] = 0;
    }

    public void changeLoaction(int x,int y){
        playBoboEntity.setX(x);
        playBoboEntity.setY(y);
    }

}
