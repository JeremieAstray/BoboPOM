package boboPOM.service;

import boboPOM.Entity.MainBoboEntity;
import boboPOM.util.Config;
import boboPOM.util.MsgQueue;
import boboPOM.util.SocketLink;

import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

/**
 * Created by Jeremie on 14-3-10.
 */
public class MainBoboService extends BaseService {

    private MsgQueue<MainBoboEntity> mainBoboMsg;
    private MainBoboEntity mainBoboEntity;

    public MainBoboService(MsgQueue msgQueue,int port){
        super();
        mainBoboEntity = new MainBoboEntity();
        mainBoboMsg = new MsgQueue<MainBoboEntity>();

        /*待修改*/
        socketLink = new SocketLink(msgQueue,mainBoboMsg,port);


        init();
    }
    public void init() {

        mainBoboEntity.setLineup(0);
        mainBoboEntity.setMainBobos(new int[16][7]);
        mainBoboEntity.setStandbyMainBobo(new int[7]);
        mainBoboEntity.setStandbyPlayBobo(new Vector<Integer>());
        Random rd = new Random();
        rd.setSeed(System.currentTimeMillis());

        //初始化主界面波波
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 7; j++)
                mainBoboEntity.getMainBobos()[i][j] = Config.NONE;
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 7; j++) {
                int temp = rd.nextInt(101) + 1;
                if (temp <= 25)
                    mainBoboEntity.getMainBobos()[i][j] = Config.BLUE;
                else if (temp <= 50)
                    mainBoboEntity.getMainBobos()[i][j] = Config.RED;
                else if (temp <= 75)
                    mainBoboEntity.getMainBobos()[i][j] = Config.GREEN;
                else if (temp <= 100)
                    mainBoboEntity.getMainBobos()[i][j] = Config.YELLOW;
                else
                    mainBoboEntity.getMainBobos()[i][j] = Config.WHITE;
            }
        //初始化备用波波
        for (int i = 0; i < 7; i++) {
            int temp = rd.nextInt(101) + 1;
            if (temp <= 25)
                mainBoboEntity.getStandbyMainBobo()[i] = Config.BLUE;
            else if (temp <= 50)
                mainBoboEntity.getStandbyMainBobo()[i] = Config.RED;
            else if (temp <= 75)
                mainBoboEntity.getStandbyMainBobo()[i] = Config.GREEN;
            else if (temp <= 100)
                mainBoboEntity.getStandbyMainBobo()[i] = Config.YELLOW;
            else
                mainBoboEntity.getStandbyMainBobo()[i] = Config.WHITE;
        }

        //初始玩的备用波波
        for (int i = 0; i < 13; i++) {
            int temp = rd.nextInt(100) + 1;
            if (temp <= 25)
                mainBoboEntity.getStandbyPlayBobo().add(Config.BLUE);
            else if (temp <= 50)
                mainBoboEntity.getStandbyPlayBobo().add(Config.RED);
            else if (temp <= 75)
                mainBoboEntity.getStandbyPlayBobo().add(Config.GREEN);
            else
                mainBoboEntity.getStandbyPlayBobo().add(Config.YELLOW);
        }
    }

    public int[] getAllStandbyPlayBobo() {
        Iterator<Integer> it = mainBoboEntity.getStandbyPlayBobo().listIterator();
        int[] bobos = new int[13];
        for (int i = 0; i < 13; i++) {
            if (it.hasNext())
                bobos[i] = it.next();
        }
        return bobos;
    }

    public int[] get2StandbyPlayBobo() {
        Vector<Integer> standbyPlayBobo = mainBoboEntity.getStandbyPlayBobo();
        int[] bobos = new int[2];
        bobos[0] = standbyPlayBobo.firstElement();
        standbyPlayBobo.removeElementAt(0);
        bobos[1] = standbyPlayBobo.firstElement();
        standbyPlayBobo.removeElementAt(0);

        Random rd = new Random();
        rd.setSeed(System.currentTimeMillis());
        for (int i = 0; i < 2; i++) {
            int temp = rd.nextInt(100) + 1;
            if (temp <= 25)
                standbyPlayBobo.add(Config.BLUE);
            else if (temp <= 50)
                standbyPlayBobo.add(Config.RED);
            else if (temp <= 75)
                standbyPlayBobo.add(Config.GREEN);
            else
                standbyPlayBobo.add(Config.YELLOW);
        }

        return bobos;
    }
}
