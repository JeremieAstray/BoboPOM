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
    private AudioClip[] effects;
    private File pathFile;
    private String[] names;
    private int playing = -1;

    public EffectMedia(String path) {
        pathFile = new File(path);
        File[] files = pathFile.listFiles();
        names = new String[files.length - 1];
        effects = new AudioClip[files.length - 1];
        for (int i = 0; i < names.length; i++) {
            names[i] = files[i].getName();
            effects[i] = new AudioClip(files[i].toURI().toString());
        }
    }

    public void stop() {
        if (effect != null) {
            effect.stop();
        }
    }

    public boolean play(int playing) {
        if (playing >= 0 && playing < names.length) {
            if (this.playing != playing) {
                this.playing = playing;
                effect = effects[playing];
            }
            effect.play();
            return true;
        }
        return false;
    }

    public boolean play(String name) {
        boolean isExist = false;
        for (int i = 0; i < names.length; i++) {
            if (names[i].equals(name)) {
                isExist = true;
                effect = effects[i];
                this.playing = i;
                effect.play();
                break;
            }
        }
        return isExist;
    }

    public boolean isPlaying() {
        if (effect != null) {
            return effect.isPlaying();
        }
        return false;
    }

    public void setVolume(int value) {
        for (AudioClip effect1 : effects) {
            effect1.setVolume(value * 1.0 / 5);
        }
    }
}
