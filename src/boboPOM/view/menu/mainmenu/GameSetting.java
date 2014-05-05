/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boboPOM.view.menu.mainmenu;

import boboPOM.config.Config;
import boboPOM.view.menu.ImageEditor;
import javafx.scene.Parent;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author:feng
 */
public class GameSetting extends Parent{
    private int GWidth;
    private int GHeight;
    
    private ImageView borderView;
    
    private Slider BGMVolumeSlider;
    private Slider SoundEffectSlider;
    private HBox BGMHBox;
    private HBox SFHBox;
    private VBox vBox;
    
    public GameSetting() {
        init();
        
        AnchorPane anchorPane = new AnchorPane(borderView, vBox);
        AnchorPane.setTopAnchor(vBox, Double.valueOf(50));
        AnchorPane.setLeftAnchor(vBox, Double.valueOf(30));
        
        this.getChildren().add(anchorPane);
    }
    
    private void init(){
        Image border = Config.getMemuImages().get(4);
        ImageEditor imageEditor = new ImageEditor(20, 30, 30, 30);
        border = imageEditor.ChangeWidth(border, 300);
        border = imageEditor.ChangeHeight(border, 250);
        borderView = new ImageView(border);
        this.GWidth =(int)border.getWidth();
        this.GHeight = (int)border.getHeight();
        
        BGMVolumeSlider = new Slider(0, 10, 10);
        SoundEffectSlider = new Slider(0, 10, 10);
        BGMVolumeSlider.setShowTickMarks(true);
        BGMVolumeSlider.setMinSize(200,50);
        BGMVolumeSlider.setShowTickLabels(true);
        BGMVolumeSlider.setMajorTickUnit(1f);
        BGMVolumeSlider.setBlockIncrement(1f);
        
        Text BGMText = new Text("音乐音量");
        Text SFText = new Text("音效音量");
        
        BGMHBox = new HBox(BGMText, BGMVolumeSlider);
        SFHBox = new HBox(SFText, SoundEffectSlider);
        
        vBox = new VBox(10, BGMHBox, SFHBox);
    }

    /**
     * @return the BGMVolumeSlider
     */
    public Slider getBGMVolumeSlider() {
        return BGMVolumeSlider;
    }

    /**
     * @return the SoundEffectSlider
     */
    public Slider getSoundEffectSlider() {
        return SoundEffectSlider;
    }

    /**
     * @return the GWidth
     */
    public int getGWidth() {
        return GWidth;
    }

    /**
     * @return the GHeight
     */
    public int getGHeight() {
        return GHeight;
    }
}
