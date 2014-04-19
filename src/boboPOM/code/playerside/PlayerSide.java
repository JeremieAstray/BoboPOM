
package boboPOM.code.playerside;

import boboPOM.config.Utils;
import boboPOM.config.Config;
import boboPOM.code.basic.Bobo;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author yorlbgy
 */
public class PlayerSide extends StackPane {

    private Shadow shadow;
    private ImageView personage;
    private ImageView wol;
    private ImageView sTip;
            
    public PlayerSide(boolean p1, int personage) {
        this.getChildren().add(new ImageView(Config.getBackgrounds().get(1)));
        this.personage = new ImageView(Config.getPersonages().get(personage));
        this.personage.setTranslateY(-Config.getSCREEN_HEIGHT() / 16);
        this.getChildren().add(this.personage);
        shadow = new Shadow(this);
        this.getChildren().add(shadow);
        this.wol = new ImageView(Config.getEffects().get(10));
        this.sTip = new ImageView(Config.getEffects().get(6));

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 16; j++) {
                this.getChildren().add(new Bobo(this));
                addBobo(new Bobo(this), i, j);
            }
        }
        if (p1) {
            this.setTranslateX(-Config.getSCREEN_WIDTH() / 4);
        } else {
            this.setTranslateX(Config.getSCREEN_WIDTH() / 4);
        }
        this.setTranslateY(-Config.getSCREEN_HEIGHT() / 23);

//         for(int i=0;i<7;i++){
//          for(int j=0;j<3;j++){
//          addBobo(new Bobo(this,0,4),i,j);}
//      }
    }

    public void setPersonage(int personage) {
        this.personage.setImage(Config.getPersonages().get(personage));
        this.personage.setStyle("-fx-opacity: 0.5;");
    }

    public double getPosX(int x) {
        return Config.getBobosImages().get(0).get(0).getWidth() * (x - 3) - 4;
    }

    public double getPosY(int y) {
        return Config.getBobosImages().get(0).get(2).getHeight() * (6 - y) * 0.93 - 9;
    }

    public Bobo getBobo(int x, int y) {
        if (x < 0 || x > 6 || y < 0 || y > 15) {
            return null;
        }
        Node node = this.getChildren().get(Utils.getIndexOfBobo(x, y));
        return (Bobo) node;
    }

    public void deleteBobo(int x, int y) {
        this.getChildren().set(Utils.getIndexOfBobo(x, y), new Bobo(this));
        addBobo(new Bobo(this), x, y);
    }

    public boolean addBobo(Bobo bobo, int x, int y) {
        if (getBobo(x, y).isNull()) {
            bobo.setPlayerSide(this);
            bobo.setIndexX(x);
            bobo.setIndexY(y);
            this.getChildren().set(Utils.getIndexOfBobo(x, y), bobo);
            return true;
        } else {
            return false;
        }
    }

    public boolean moveBobo(int x, int y, int newX, int newY) {
        Bobo bo = getBobo(x, y);
        deleteBobo(x, y);
        if (newY > 15) {
            return true;
        }
        if(!addBobo(bo, newX, newY)) {addBobo(bo,x,y);return false;}
        return true;
    }

    public boolean moveBobo(Bobo bo, Bobo bi) {
        if (bi == null) {
            return false;
        }
        return moveBobo(bo.getIndexX(), bo.getIndexY(), bi.getIndexX(), bi.getIndexY());
    }

    public  LinkedList<Bobo> getAllBobo(int boboState) {
        LinkedList<Bobo> list = new LinkedList<>();
        for (int i = 0; i < 16 * 7; i++) {
            Bobo bo = (Bobo) this.getChildren().get(i + 3);
            if(bo.getState() == boboState)
            list.add(bo);
        }
        return list;
    }

    public void addLine(ArrayList<Bobo> list) {
        for (int i = 15; i >= 0; i--) {
            for (int j = 0; j < 7; j++) {
                Bobo bo = (Bobo) this.getChildren().get(Utils.getIndexOfBobo(j, i));
                if (bo.isNull()) {
                    continue;
                }
                double mt = bo.getTranslateY();
                moveBobo(bo, bo.getUp());
                bo.setTranslateY(mt);
                bo.changeState(Bobo.STATE_RISING);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            this.addBobo(list.get(i), i, 0);
            list.get(i).changeState(Bobo.STATE_RISING);
        }
    }

    public int getDeep() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 7; j++) {
                if (((Bobo) this.getChildren().get(Utils.getIndexOfBobo(j, i))).isNull()) {
                    return i;
                }
            }
        }
        return 0;
    }
    public Shadow getShadow(){
        return shadow;
    }
    public ImageView getWol(){
        return wol;
    }
    
    public void addSTip(){
       this.getChildren().add(this.sTip);
    }
    
    public void removeSTip(){
        this.getChildren().remove(sTip);
    }
    
    public ImageView getSTip(){
        return sTip;
    }
}
