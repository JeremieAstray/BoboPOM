package boboPOM.media;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

import java.net.URL;
import java.util.ArrayList;

/**
 * @author:feng
 */
public class BGMMedia {

    private Media music;
    private MediaPlayer musicPlayer;
    private double volume = 1;

    private int playingMusic = -1; //表示当前播放音乐，-1表示当前无音乐

    private ArrayList<URL> urls;

    public BGMMedia() {
        urls = new ArrayList<URL>();
    }

    public void addURL(URL url) {
        urls.add(url);
    }

    public void stopMusic() {
        if (playingMusic != -1) {
            musicPlayer.stop();
        }
    }

    private void play(boolean loop, int num) {
        if (playingMusic != num) {
            this.stopMusic();
            this.music = new Media(urls.get(num) + "");
            this.musicPlayer = new MediaPlayer(this.music);
            this.playingMusic = num;
            this.musicPlayer.setVolume(this.volume);
            if (loop) {
                musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            } else {
                musicPlayer.setCycleCount(1);
            }
            musicPlayer.play();
        }
    }

    public boolean playMusic(int music, boolean loop) {
        if (music >= 0 && music < urls.size()) {
            play(loop, music);
            return true;
        } else {
            return false;
        }
    }

    public Status getStatus() {
        return musicPlayer.getStatus();
    }

    public void setVolume(int volume) {
        this.volume = volume * 1.0 / 5;
        if (this.musicPlayer != null) {
            this.musicPlayer.setVolume(this.volume);
        }
    }

}
