package boboPOM.code.playerside;

import boboPOM.config.Config;
import javafx.scene.image.ImageView;

/**
 * @author yorlbgy
 */
public class Shadow extends ImageView {

    private boolean warning;
    private int index;
    private PlayerSide parent;

    public Shadow(PlayerSide parent) {
        this.setStyle("-fx-opacity: 0.5;");
        this.parent = parent;
        warning = false;
        setScaleY(2.3);
        this.setTranslateY(Config.getBobosImages().get(0).get(0).getHeight());
        this.setTranslateX(-3);
        out();
        changeColor();
    }

    private void changeColor() {
        if (warning) {
            this.setImage(Config.getEffects().get(2));
        } else {
            this.setImage(Config.getEffects().get(1));
        }
    }

    public void setWarning(boolean warning) {
        this.warning = warning;
        changeColor();
    }

    public void out() {
        this.index = -1;
        this.setVisible(false);
    }

    public void setIndex(int index) {
        this.index = index;
        if (index >= 0) {
            this.setVisible(true);
            this.setTranslateX(parent.getPosX(index));
        } else {
            out();
        }
    }
}
