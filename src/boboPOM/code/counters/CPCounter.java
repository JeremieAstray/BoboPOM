
package boboPOM.code.counters;

import boboPOM.config.Config;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.ArrayList;

import static boboPOM.config.Config.CP_MAX;
import static boboPOM.config.Config.CP_MIN;
import static boboPOM.code.anime.Transitions.newSt;

/**
 *
 * @author yorlbgy
 */
public class CPCounter extends Parent {

    private boolean p1;
    private int CP;
    private int nowCP = 0;
    private ContentPane content;
    private boolean shinning;

    public CPCounter(boolean p1) {
        this.p1 = p1;
        init();
        upCPAnimePlay();
    }

    public int useCP() {
        int addLines = CP / 100;
        CP = CP_MIN;
        nowCP = CP_MIN;
        shinning = false;
        content.setCP(nowCP);
        content.sButtonShinning(shinning);
        return addLines;
    }

    public boolean isShinning() {
        return shinning;
    }

    public synchronized void addCP(int add) {
        int newCP = CP + add;
        if (newCP <= CP_MAX) {
            CP = newCP;
        } else {
            CP = CP_MAX;
        }
        if (CP > 100) {
            shinning = true;
        }
        content.sButtonShinning(shinning);
        //Anime setCP;
    }

    public int getNowCP() {
        return nowCP;
    }

    //network view
    public void setNowCP(int cp){
        this.nowCP = cp;
        this.CP = cp;
        this.content.setCP(cp);
    }
    public ContentPane getContent() {
        return content;
    }

    private void init() {
        CP = 0;
        shinning = false;
        content = new ContentPane(p1);
        this.getChildren().add(content);
        if (p1) {
            this.setTranslateX(-30);
            this.setTranslateY(-90);
        } else {
            this.setTranslateX(30);
            this.setTranslateY(90);
        }
        content.setPrefSize(200, 120);
    }

    private void upCPAnimePlay() {
       Timeline tl = new Timeline();
       tl.setAutoReverse(false);
       tl.setCycleCount(Timeline.INDEFINITE);
       KeyFrame kf = new KeyFrame(Duration.millis(7),new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent arg0) {
           //    System.out.println(CP);
               if(nowCP<CP){
               nowCP++;        
               content.setCP(nowCP);
               }
               else return;
           }
       });
       tl.getKeyFrames().add(kf);
       tl.play();
    }

    void setShinning(boolean b) {
        this.shinning = b;
        this.content.sButtonShinning(shinning);
    }

    private class ContentPane extends StackPane {

        private ImageView CPH, CPT, CP, sButton,shinning;
        private ProgressBar processor;
        private ArrayList<Image> CPHImages, CPImages;

        public ContentPane(boolean p1) {
            this.CPHImages = Config.getNumbers().get(1);
            this.CPImages = Config.getNumbers().get(0);
            CPH = new ImageView(CPHImages.get(0));
            CPH.setTranslateX(-CPHImages.get(0).getWidth() / 2);
            CPH.setTranslateY(-CPHImages.get(0).getHeight() / 4);
            CPH.setVisible(false);
            CPT = new ImageView(CPImages.get(0));
            CPT.setTranslateX(CPImages.get(0).getWidth() / 2);
            CPT.setTranslateY(-CPImages.get(0).getHeight() / 2);
            CPT.setVisible(false);
            CP = new ImageView(CPImages.get(0));
            CP.setTranslateX(3 * CPImages.get(0).getWidth() / 2);
            CP.setTranslateY(-CPImages.get(0).getHeight() / 2);
            processor = new ProgressBar(0);
            processor.setTranslateY(CPH.getImage().getHeight() / 2 + Config.getEffects().get(5).getHeight() / 2);
            sButton = new ImageView(Config.getEffects().get(4));
            shinning = new ImageView(Config.getEffects().get(13));
            if (p1) {
                sButton.setTranslateX(processor.getTranslateX() - Config.getEffects().get(5).getWidth() / 2 - sButton.getImage().getWidth() / 2);
            } else {
                sButton.setTranslateX(processor.getTranslateX() + Config.getEffects().get(5).getWidth() / 2 + sButton.getImage().getWidth() / 2);              
                processor.setRotate(180);
            }
            processor.setStyle("-fx-accent:red;");
            sButton.setTranslateY(sButton.getImage().getHeight());
            sButton.setVisible(false);
             shinning.setTranslateX(sButton.getTranslateX());
             shinning.setTranslateY(sButton.getTranslateY());
             shinning.setVisible(true);
            this.getChildren().addAll(CPH, CPT, CP, processor, sButton);
            shinningAnimePlay();
        }

        public void setCP(int CP) {
            int h = CP / 100;
            int t = CP % 100 / 10;
            int c = CP % 10;
            CPH.setImage(CPHImages.get(h));
            CPH.setVisible(h > 0);
            CPT.setImage(CPImages.get(t));
            CPT.setVisible(h > 0 || t > 0);
            this.CP.setImage(CPImages.get(c));
            processor.setProgress(CP * 1.0 / 999);
        }

        public void sButtonShinning(boolean shinning) {
            sButton.setVisible(shinning);
            if(shinning == false){
                this.getChildren().remove(this.shinning);
            }
            else{
                if(!this.getChildren().contains(this.shinning))
                this.getChildren().add(this.shinning);
            }
            //anime
        }

      private void shinningAnimePlay() {
       ScaleTransition st = newSt(Duration.millis(700), 0.9, true);
        st.setFromX(0.3);
        st.setFromY(0.3);
        st.setAutoReverse(false);
        st.setCycleCount(Transition.INDEFINITE);
        st.setInterpolator(Interpolator.EASE_IN);
        st.setNode(shinning);
        st.play();
        }
    }
}
