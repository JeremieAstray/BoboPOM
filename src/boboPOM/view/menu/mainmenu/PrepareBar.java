/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boboPOM.view.menu.mainmenu;

import boboPOM.config.Config;
import boboPOM.view.menu.mainmenu.netmenu.SocketMenu;
import javafx.scene.layout.AnchorPane;

/**
 * @author:feng
 */
public class PrepareBar extends SocketMenu {
    private boolean peerPrepared = false;
    private boolean ownPrepared = false;

    private String prepared = "对方已做好准备";


    public PrepareBar() {
        this("等待对方做好准备", "对方已做好准备");
    }

    public PrepareBar(String prepare1, String prepare2) {
        super(prepare1, 260, 75);
        this.prepared = prepare2;
        this.setVisible(false);
        this.timeline.stop();
        AnchorPane.setTopAnchor(this.waitText, Double.valueOf(26));
    }

    public boolean isPeerPrepared() {
        return peerPrepared;
    }

    public void setPeerPrepared(boolean isPrepared) {
        this.peerPrepared = isPrepared;
        this.setVisible(isPrepared);
        if (isPrepared) {
            this.timeline.stop();
            this.waitText.setText(prepared);
            AnchorPane.setLeftAnchor(this.waitText, Double.valueOf(40));
            this.setTranslateX(Config.getSCREEN_WIDTH() - this.getSWidth() - 10);
            this.setTranslateY(0);
        }
    }

    public boolean isOwnPrepared() {
        return ownPrepared;
    }

    public void setOwnPrepared(boolean isPrepared) {
        this.ownPrepared = isPrepared;
        this.setVisible(isPrepared);
        if (isPrepared) {
            this.timeline.play();
            AnchorPane.setLeftAnchor(waitText, Double.valueOf(this.getSWidth() / 2
                    - 14 * waitString.length()));
            this.setTranslateX(Config.getSCREEN_WIDTH() / 2 - this.getSWidth() / 2);
            this.setTranslateY(Config.getSCREEN_HEIGHT() / 2 - this.getSHeight() / 2);
        }
    }

    public void reset() {
        this.setVisible(false);
        this.ownPrepared = false;
        this.peerPrepared = false;
    }
}
