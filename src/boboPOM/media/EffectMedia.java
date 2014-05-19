package boboPOM.media;

import javafx.scene.media.AudioClip;

import java.net.URL;
import java.util.ArrayList;

/**
 * @author:feng
 */
public class EffectMedia {

    private AudioClip effect;
    private int playing = -1;
    private ArrayList<AudioClip> effects;

    public EffectMedia() {

        effects = new ArrayList<AudioClip>();
    }

    public void addEffect(URL url) {
        AudioClip audioClip = new AudioClip(url + "");
        effects.add(audioClip);
    }

    public void stop() {
        if (effect != null) {
            effect.stop();
        }
    }

    public boolean play(int playing) {
        if (playing >= 0 && playing < effects.size()) {
            if (this.playing != playing) {
                this.playing = playing;
                effect = effects.get(playing);
            }
            effect.play();
            return true;
        }
        return false;
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
