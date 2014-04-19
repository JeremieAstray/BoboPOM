package boboPOM.service;

import boboPOM.Entity.MainBoboEntity;
import boboPOM.config.Config;
import boboPOM.net.SocketLink;

import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

/**
 * Created by Jeremie on 14-3-10.
 */
public class MainBoboService {

    private MainBoboEntity mainBoboEntity;

    public MainBoboService(){
        mainBoboEntity = new MainBoboEntity();
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
                mainBoboEntity.getMainBobos()[i][j] = Config.NULL;
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 7; j++) {
                int temp = rd.nextInt(101) + 1;
                if (temp <= 25)
                    mainBoboEntity.getMainBobos()[i][j] = Config.TYPE_BLUE;
                else if (temp <= 50)
                    mainBoboEntity.getMainBobos()[i][j] = Config.TYPE_RED;
                else if (temp <= 75)
                    mainBoboEntity.getMainBobos()[i][j] = Config.TYPE_GREEN;
                else if (temp <= 100)
                    mainBoboEntity.getMainBobos()[i][j] = Config.TYPE_YELLOW;
                else
                    mainBoboEntity.getMainBobos()[i][j] = Config.TYPE_WHITE;
            }
        //初始化备用波波
        for (int i = 0; i < 7; i++) {
            int temp = rd.nextInt(101) + 1;
            if (temp <= 25)
                mainBoboEntity.getStandbyMainBobo()[i] = Config.TYPE_BLUE;
            else if (temp <= 50)
                mainBoboEntity.getStandbyMainBobo()[i] = Config.TYPE_RED;
            else if (temp <= 75)
                mainBoboEntity.getStandbyMainBobo()[i] = Config.TYPE_GREEN;
            else if (temp <= 100)
                mainBoboEntity.getStandbyMainBobo()[i] = Config.TYPE_YELLOW;
            else
                mainBoboEntity.getStandbyMainBobo()[i] = Config.TYPE_WHITE;
        }

        //初始玩的备用波波
        for (int i = 0; i < 13; i++) {
            int temp = rd.nextInt(100) + 1;
            if (temp <= 25)
                mainBoboEntity.getStandbyPlayBobo().add(Config.TYPE_BLUE);
            else if (temp <= 50)
                mainBoboEntity.getStandbyPlayBobo().add(Config.TYPE_RED);
            else if (temp <= 75)
                mainBoboEntity.getStandbyPlayBobo().add(Config.TYPE_GREEN);
            else
                mainBoboEntity.getStandbyPlayBobo().add(Config.TYPE_YELLOW);
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
                standbyPlayBobo.add(Config.TYPE_BLUE);
            else if (temp <= 50)
                standbyPlayBobo.add(Config.TYPE_RED);
            else if (temp <= 75)
                standbyPlayBobo.add(Config.TYPE_GREEN);
            else
                standbyPlayBobo.add(Config.TYPE_YELLOW);
        }

        return bobos;
    }

    public void send(SocketLink socketLink) {
        socketLink.send(this.mainBoboEntity);
    }
}
