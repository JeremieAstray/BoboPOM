/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boboPOM.view.menu.mainmenu;

import boboPOM.config.Config;
import boboPOM.view.menu.ImageEditor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
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
    private ComboBox<String> ResolutionBox;
    private GridPane gridPane;
    
    public GameSetting() {
        init();
        
        AnchorPane anchorPane = new AnchorPane(borderView, gridPane);
        AnchorPane.setTopAnchor(gridPane, Double.valueOf(60));
        AnchorPane.setLeftAnchor(gridPane, Double.valueOf(45));
        
        this.getChildren().add(anchorPane);
    }
    
    private void init(){
        Image border = Config.getMemuImages().get(4);
        ImageEditor imageEditor = new ImageEditor(20, 30, 30, 30);
        border = imageEditor.ChangeWidth(border, 400);
        border = imageEditor.ChangeHeight(border, 250);
        borderView = new ImageView(border);
        this.GWidth =(int)border.getWidth();
        this.GHeight = (int)border.getHeight();
        
        ObservableList resolutions = FXCollections.observableArrayList(
                "全屏","窗口"
        );
        ResolutionBox = new ComboBox<String>(resolutions);
        ResolutionBox.setMinWidth(100);
        
        BGMVolumeSlider = new Slider(0, 5, 5);
        SoundEffectSlider = new Slider(0, 5, 5);
        BGMVolumeSlider.setShowTickMarks(true);
        BGMVolumeSlider.setMinWidth(200);
        BGMVolumeSlider.setShowTickLabels(true);
        BGMVolumeSlider.setMajorTickUnit(1);
        BGMVolumeSlider.setBlockIncrement(1);
        BGMVolumeSlider.setMinorTickCount(0);
        
        SoundEffectSlider.setShowTickMarks(true);
        SoundEffectSlider.setShowTickLabels(true);
        SoundEffectSlider.setMajorTickUnit(1);
        SoundEffectSlider.setBlockIncrement(1);
        SoundEffectSlider.setMinorTickCount(0);
        
        Font font = new Font(20);
        Text resolutionText = new Text("屏幕分辨率");
        Text BGMText = new Text("音乐音量");
        Text SFText = new Text("音效音量");
        resolutionText.setFont(font);
        BGMText.setFont(font);
        SFText.setFont(font);
        
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(20);
        gridPane.add(resolutionText, 0, 0);
        gridPane.add(ResolutionBox, 1, 0);
        gridPane.add(BGMText, 0, 1);
        gridPane.add(BGMVolumeSlider, 1, 1);
        gridPane.add(SFText, 0, 2);
        gridPane.add(SoundEffectSlider, 1, 2);
        
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
