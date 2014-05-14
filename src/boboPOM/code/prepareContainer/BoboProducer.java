/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boboPOM.code.prepareContainer;

import java.util.Stack;

/**
 *
 * @author:feng
 */
public class BoboProducer {

    private int[][] bobo;
    private Point point;
    private int[][] mark;
    private int[][] mark2;
    private Stack<Point> stack;

    public BoboProducer() {
    }

    public int[][] BoboProduce(int numOfline) {
        bobo = new int[numOfline][7];
        for (int i = 0; i < bobo.length; i++) {
            for (int j = 0; j < bobo[i].length; j++) {
                bobo[i][j] = CreateBobo();
            }
        }
        mark = new int[bobo.length][bobo[0].length];
        mark2 = new int[bobo.length][bobo[0].length];
        setZero(mark2);
        stack = new Stack<Point>();
        for (int i = 0; i < bobo.length; i++) {
            for (int j = 0; j < bobo[i].length; j++) {
                if (mark2[i][j] == 0) {
                    setZero(mark);
                    stack.clear();
                    isSame(i, j, i, j);
                    setMark();
                }
            }
        }
        return bobo;
    }

    private void setMark() {
        if (stack.size() > 2) {
            while (!stack.empty()) {
                point = stack.pop();
                if (stack.size() >= 1) {
                    reSet(point.getX(), point.getY());
                }
                mark2[point.getX()][point.getY()] = 1;
            }
        } else{
            while(!stack.empty()){
                point = stack.pop();
                mark2[point.getX()][point.getY()] = 1;
            }
        }
    }

    private void setZero(int[][] n) {
        for (int i = 0; i < n.length; i++) {
            for (int j = 0; j < n[i].length; j++) {
                n[i][j] = 0;
            }
        }
    }

    private void reSet(int i, int j) {
        int[] signal = new int[5];
        if (i - 1 >= 0) {
            signal[bobo[i - 1][j]] = 1;
        }
        if (j - 1 >= 0) {
            signal[bobo[i][j - 1]] = 1;
        }
        if (i + 1 < bobo.length) {
            signal[bobo[i + 1][j]] = 1;
        }
        if (j + 1 < bobo[0].length) {
            signal[bobo[i][j + 1]] = 1;
        }

        int add = (int) (Math.random() * 2);
        if (add == 0) {
            for (int k = 0; k < signal.length - 1; k++) {
                if (signal[k] == 0) {
                    bobo[i][j] = k;
                    break;
                }
            }
        } else {
            for (int k = signal.length - 2; k >= 0; k--) {
                if (signal[k] == 0) {
                    bobo[i][j] = k;
                    break;
                }
            }
        }
    }

    private boolean isSame(int xo, int yo, int xc, int yc) {
        if (xc < 0 || yc < 0 || xc >= bobo.length || yc >= bobo[0].length) {
            return false;
        } else {
            if (mark[xc][yc] == 0 && bobo[xc][yc] == bobo[xo][yo]) {
                point = new Point(xc, yc);
                mark[xc][yc] = 1;
                stack.push(point);
                isSame(xo, yo, xc - 1, yc);
                isSame(xo, yo, xc, yc - 1);
                isSame(xo, yo, xc + 1, yc);
                isSame(xo, yo, xc, yc + 1);
                return true;
            }
        }
        return false;
    }

    private int CreateBobo() {
        int num1 = (int) (Math.random() * 30);
        if (num1 >= 1) {
            return (int) (Math.random() * 4);
        } else {
            return 4;
        }
    }

    public static void main(String[] args) {
        BoboProducer boboProducer = new BoboProducer();
        boboProducer.BoboProduce(10);
    }

    public class Point {

        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * @return the x
         */
        public int getX() {
            return x;
        }

        /**
         * @param x the x to set
         */
        public void setX(int x) {
            this.x = x;
        }

        /**
         * @return the y
         */
        public int getY() {
            return y;
        }

        /**
         * @param y the y to set
         */
        public void setY(int y) {
            this.y = y;
        }

    }
}
