package boboPOM.media;

import javafx.scene.media.AudioClip;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author:feng
 */
public class EffectMedia {

    private AudioClip effect;
    private String path;
    private String[] names;
    private int playing = -1;
    
    public EffectMedia(File path) {
        File file = path;
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
