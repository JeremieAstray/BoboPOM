/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boboPOM.media;

import boboPOM.config.Config;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.scene.media.AudioClip;

/**
 *
 * @author:feng
 */
public class EffectMedia {

    private AudioClip effect;
    private String path;
    private String[] names;
    private int playing = -1;
    
    public EffectMedia(String path) {
        URI uri = null;
        this.path = path;
        try {
            uri = new URI(path);
        } catch (URISyntaxException ex) {
           ex.printStackTrace();
        }
        File file = new File(uri);
        File[] files = file.listFiles();
        names = new String[files.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = files[i].getName();
        }
    }


    public void stop() {
        effect.stop();
    }

    public boolean play(int playing) {
        if (playing >= 0 && playing < names.length) {
            if (this.playing != playing) {
                this.playing = playing;
                effect = new AudioClip(path + "/" + names[this.playing]);
            }
            effect.play();
            return true;
        }
        return false;
    }
    public boolean play(String name){
        boolean isExist = false;
        for(int i=0;i<names.length;i++){
            if(names[i].equals(name)){
                isExist = true;
                effect = new AudioClip(path + "/" + name);
                effect.play();
                break;
            }
        }
        return isExist;
    }
}
