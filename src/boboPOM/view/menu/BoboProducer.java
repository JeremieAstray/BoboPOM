/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boboPOM.view.menu;

import java.util.Random;

/**
 *
 * @author:feng
 */
public class BoboProducer {

    public BoboProducer() {
    }

    public void BoboProduce(int numOfline) {
        int[][] bobo = new int[numOfline][7];
        for (int i = 0; i < bobo.length; i++) {
            for (int j = 0; j < bobo[i].length; j++) {
                bobo[i][j] = CreateBobo();
                System.out.print(bobo[i][j] + " ");
            }
            System.out.println("");
        }
        int same = 0;
        for (int i = 0; i < bobo.length; i++) {
            for (int j = 0; j < bobo[i].length; j++) {
                same = 0;
                if (bobo.length == 1) {
                    if (j != 0 && j != bobo[i].length) {
                        if (bobo[i][j] == bobo[i][j - 1]) {
                            same++;
                        }
                        if (bobo[i][j] == bobo[i][j + 1]) {
                            same++;
                        }
                    }
                }
            }
        }
    }

    private int CreateBobo() {
        int num1 = (int) (Math.random() * 30);
        if (num1 >= 1) {
            return (int) (Math.random() * 4);
        } else {
            return 5;
        }
    }

    public static void main(String[] args) {
        BoboProducer boboProducer = new BoboProducer();
        boboProducer.BoboProduce(5);
    }
}
