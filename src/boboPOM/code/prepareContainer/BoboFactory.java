package boboPOM.code.prepareContainer;

import boboPOM.code.basic.Bobo;
import boboPOM.code.basic.Brick;

import java.util.ArrayList;

/**
 * @author yorlbgy
 */
public class BoboFactory {

    public static QueueList<Brick> qp1 = new QueueList<>(), qp2 = new QueueList<>();
    public static QueueList<ArrayList<Bobo>> rp1 = new QueueList<>(), rp2 = new QueueList<>();
    public static BoboProducer bp = new BoboProducer();
    public static int numOfLine_Produced = 10;

    public static void init() {
//        qp1.clear();
//        qp2.clear();
//        rp1.clear();
//        rp2.clear();
        for (int i = 0; i < 6; i++) {
            makeBrick();
        }
        makeBobos();
    }

    public static void makeBrick() {
        int u = (int) (Math.random() * 4);
        int d = (int) (Math.random() * 4);
        qp1.add(new Brick(new Bobo(u, Bobo.STATE_BRICK), new Bobo(d, Bobo.STATE_BRICK)));
        qp2.add(new Brick(new Bobo(u, Bobo.STATE_BRICK), new Bobo(d, Bobo.STATE_BRICK)));
    }

    public static void makeBobos() {
        ArrayList<Bobo> rbobo1 = new ArrayList<>();
        ArrayList<Bobo> rbobo2 = new ArrayList<>();
        int[][] rs = bp.BoboProduce(numOfLine_Produced);
        int r;
        for (int k = 0; k < numOfLine_Produced; k++) {
            for (int i = 0; i < 7; i++) {
                r = rs[k][i];
                rbobo1.add(new Bobo(r, Bobo.STATE_RISING));
                rbobo2.add(new Bobo(r, Bobo.STATE_RISING));
            }
            rp1.add((ArrayList<Bobo>) rbobo1.clone());
            rp2.add((ArrayList<Bobo>) rbobo2.clone());
            rbobo1.clear();
            rbobo2.clear();
        }
    }
}
