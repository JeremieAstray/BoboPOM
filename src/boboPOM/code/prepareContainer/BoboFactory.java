package boboPOM.code.prepareContainer;

import boboPOM.code.basic.Bobo;
import boboPOM.code.basic.Brick;
import static java.lang.Thread.sleep;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author yorlbgy
 */
public class BoboFactory {

    public static QueueList<Brick> qp1 = new QueueList<>(), qp2 = new QueueList<>();
    public static QueueList<ArrayList<Bobo>> rp1 = new QueueList<>(), rp2 = new QueueList<>();
    public static BoboProducer bp = new BoboProducer();
    public static int numOfLine_Produced = 10;
    private static int[] p = new int[4];

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
        int u,d;
        if(qp1.isEmpty()){
             u =(int) (Math.random() * 4);
             d =(int) (Math.random() * 4);
        }else{
        int lu = qp1.getLast().getU().getType();
        int ld = qp1.getLast().getD().getType();
         u = countTheType(lu,ld);
            System.out.println("");
        lu = ld;
        ld = u;
         d = countTheType(lu,ld);
        }
        qp1.add(new Brick(new Bobo(u, Bobo.STATE_BRICK), new Bobo(d, Bobo.STATE_BRICK)));
        qp2.add(new Brick(new Bobo(u, Bobo.STATE_BRICK), new Bobo(d, Bobo.STATE_BRICK)));
    }

    private static int countTheType(int lu, int ld) {
        p = new int[]{100, 100, 100, 100};
        boolean b = lu == ld;
        int t = (int) (Math.random() * 400);
        p[lu] -= 45; 
        p[ld] -= 45;
        if(b)
        for(int i = 0 ;i < 4 ;i++){
            if(i!=lu) p[i] += 30;
        }
        else for(int i =0;i<4;i++){
            if(i!=lu&&i!=ld) p[i] += 45;
        }
        for (int i = 0; i < 4; i++) {
            t -= p[i];
            if (t < 0) {
                t = i;
                break;
//                System.out.println(p[0]+";"+p[1]+";"+p[2]+";"+p[3]+":::"+t);
            }
        }
        return t;
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
